package finalproject.bookshop.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalproject.bookshop.model.entity.UserEntity;
import finalproject.bookshop.repository.BookRepository;
import finalproject.bookshop.repository.UserRepository;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser("lucho@example.com")
@SpringBootTest
@AutoConfigureMockMvc
class BooksRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ObjectMapper objectMapper;

  private UserEntity testUser;

  @BeforeEach
  void setUp() {
    testUser = new UserEntity();
    testUser.setPassword("password");
    testUser.setUsername("gosho");
    testUser.setEmail("gosho@example.com");
    testUser.setFirstName("Gosho");
    testUser.setLastName("Goshev");

    testUser = userRepository.save(testUser);
  }

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
  }

}
