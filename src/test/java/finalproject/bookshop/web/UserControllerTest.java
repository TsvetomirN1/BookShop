package finalproject.bookshop.web;



import finalproject.bookshop.model.entity.UserEntity;
import finalproject.bookshop.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
  }

  private static final String TEST_USER_NAME = "gosho";
  private static final String TEST_USER_EMAIL = "gosho@example.com";
  private static final int TEST_USER_AGE = 12;

  @Test
  void testRegisterUser() throws Exception {
    mockMvc.perform(post("/users/register").
                    param("username", TEST_USER_NAME).
                    param("firstName", "Gosho").
                    param("lastName", "Goshev").
                    param("email", TEST_USER_EMAIL).
                    param("password", "12345").
                    param("confirmPassword", "12345").
                    with(csrf()).
                    contentType(MediaType.APPLICATION_FORM_URLENCODED)
            ).
            andExpect(status().is3xxRedirection());

    Assertions.assertEquals(1, userRepository.count());

    Optional<UserEntity> newlyCreatedUserOpt = userRepository.findByUsername(TEST_USER_NAME);

    Assertions.assertTrue(newlyCreatedUserOpt.isPresent());

    UserEntity newlyCreatedUser = newlyCreatedUserOpt.get();


  }
}
