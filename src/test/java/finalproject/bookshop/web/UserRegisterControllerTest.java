package finalproject.bookshop.web;


import finalproject.bookshop.model.entity.UserEntity;
import finalproject.bookshop.model.entity.UserRoleEntity;
import finalproject.bookshop.model.entity.enums.UserRoleEnum;
import finalproject.bookshop.repository.UserRepository;
import finalproject.bookshop.repository.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserRegisterControllerTest {

    private static final String TEST_USERNAME = "testUser";

    private UserRoleEntity userRole;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        userRoleRepository.deleteAll();

        userRole = initUserRole();
        initTestUser();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @Test
    void test_GetRegisterForm_OpensRegisterForm() throws Exception {
        mockMvc
                .perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }


    @Test
    void test_PostRegister_CreatesNewUser() throws Exception {
        mockMvc
                .perform(post("/register")
                        .param("firstName", "Test")
                        .param("lastName", "Testov")
                        .param("username", TEST_USERNAME)
                        .param("email", "test@test.bg")
                        .param("password", "test")
                        .param("confirmPassword", "test")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        assertEquals(2, userRepository.count());

        Optional<UserEntity> newlyCreatedUserOpt = userRepository.findByUsername(TEST_USERNAME);
        assertTrue(newlyCreatedUserOpt.isPresent());

        UserEntity newlyCreatedUser = newlyCreatedUserOpt.get();
        assertEquals("Test", newlyCreatedUser.getFirstName());
        assertEquals("Testov", newlyCreatedUser.getLastName());
        assertEquals("test@test.bg", newlyCreatedUser.getEmail());

    }



    @Test
    void test_PostRegisterInvalidParams_RedirectsToRegisterForm() throws Exception {
        mockMvc
                .perform(post("/register")
                        .param("firstName", "Test")
                        .param("lastName", "Testov")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"));
    }



    private UserRoleEntity initUserRole() {
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setRole(UserRoleEnum.USER);
        userRoleRepository.save(userRole);

        return userRole;
    }

    private void initTestUser() {
        UserEntity testUser = new UserEntity();
        testUser.setFirstName("user");
        testUser.setLastName("user");
        testUser.setUsername("user");
        testUser.setEmail("user@abg.bg");
        testUser.setPassword("6c0a915441e114b6820fe5dd6462a14409e0cd7ddb048137586a8a8f654d180e3269546af520416f");
        testUser.setRoles(Set.of(userRole));

        userRepository.save(testUser);

    }

}