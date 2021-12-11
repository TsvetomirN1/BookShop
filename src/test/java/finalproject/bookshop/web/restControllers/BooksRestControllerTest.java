package finalproject.bookshop.web.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalproject.bookshop.model.entity.AuthorEntity;
import finalproject.bookshop.model.entity.UserEntity;
import finalproject.bookshop.repository.AuthorRepository;
import finalproject.bookshop.repository.BookRepository;
import finalproject.bookshop.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WithMockUser("lucho@example.com")
@SpringBootTest
@AutoConfigureMockMvc
class BooksRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private ObjectMapper objectMapper;


  private AuthorEntity testAuthor;

  @BeforeEach
  void setUp() {
    testAuthor = new AuthorEntity();
    testAuthor.setFullName("MATT GREEN");
    testAuthor.setBooks(List.of());


    testAuthor = authorRepository.save(testAuthor);
  }

  @AfterEach
  void tearDown() {
    authorRepository.deleteAll();
    bookRepository.deleteAll();
  }


  void testGetBookById() {



  }


}
