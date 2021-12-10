package finalproject.bookshop.security;

import finalproject.bookshop.model.entity.UserEntity;
import finalproject.bookshop.model.entity.UserRoleEntity;
import finalproject.bookshop.model.entity.enums.UserRoleEnum;
import finalproject.bookshop.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    private UserEntity testUser;
    private UserRoleEntity adminRole, userRole;
    private UserDetailsServiceImpl serviceImplTest;


    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void init() {
        serviceImplTest = new UserDetailsServiceImpl(mockUserRepository);

        adminRole = new UserRoleEntity();
        adminRole.setRole(UserRoleEnum.ADMIN);

        userRole = new UserRoleEntity();
        userRole.setRole(UserRoleEnum.USER);

        testUser = new UserEntity();
        testUser.setUsername("tes");
        testUser.setEmail("tes@tes.bg");
        testUser.setPassword("test");
        testUser.setRoles(Set.of(adminRole, userRole));
    }

    @Test
    void testUserNotFound() {
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> serviceImplTest.loadUserByUsername("Invalid User"));

    }

    @Test
    void testUserFound() {

        Mockito.when(mockUserRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));


          var actual = serviceImplTest.loadUserByUsername(testUser.getUsername());

          Assertions.assertEquals(actual.getUsername(),testUser.getUsername());
          String actualRoles = actual.getAuthorities().stream()
                  .map(GrantedAuthority::getAuthority)
                  .collect(Collectors.joining(", "));

          String expectedRoles = "ROLE_ADMIN, ROLE_USER";

          Assertions.assertEquals(expectedRoles,actualRoles);
    }
}