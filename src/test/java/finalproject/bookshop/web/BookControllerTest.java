package finalproject.bookshop.web;

import finalproject.bookshop.model.entity.*;
import finalproject.bookshop.model.entity.enums.UserRoleEnum;
import finalproject.bookshop.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {


    private static final String TEST_BOOK_TO_ADD_TITLE = "TESTBOOK";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ImageRepository imageRepository;

    private UserRoleEntity adminRole;
    private UserEntity testUser;
    private CategoryEntity testCategory;
    private AuthorEntity testAuthor;
    private List<BookEntity> testBooks;
    private ImageEntity testImage;

    @BeforeEach
    void setUp() {
//        userRepository.deleteAll();
//        userRoleRepository.deleteAll();
//        bookRepository.deleteAll();
//        authorRepository.deleteAll();
//        categoryRepository.deleteAll();
//
//
//        adminRole = initAdminRole();
//        testUser = initTestUser();
//
//        testCategory = initCategory();
//        testAuthor = initAuthor();
//        testImage = initImage();
//        testBooks = initBooks();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin")
    void test_GetALlBooksPage_LoadsCorrectly() throws Exception {
        mockMvc
                .perform(get("/admin/all-books"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/all-books"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "ADMIN")
    void test_GetAddBookForm_OpensForm() throws Exception {
        mockMvc
                .perform(get("/admin/add-book"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/add-book"));
    }


    @Test
    @WithMockUser(username = "testUser", roles = "ADMIN")
    void test_AddBook_SavesNewBookCorrectly() throws Exception {

        ImageEntity image = new ImageEntity();
        image.setUrl("image");
        CategoryEntity category = new CategoryEntity();
        category.setCategory("category");
        AuthorEntity author = new AuthorEntity();
        author.setFullName("Matt Green");


        ResultActions resultActions =

                mockMvc
                        .perform(multipart("/admin/add-book")
                                .param("title", TEST_BOOK_TO_ADD_TITLE)
                                .param("price", String.valueOf(5))
                                .param("year", String.valueOf(2020))
                                .param("description", "description")
                                .param("categories", category.getCategory())
                                .param("authorFullName", author.getFullName())
                                .param("image", image.getUrl())
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        );

        Optional<BookEntity> newlyCreatedBookOpt = bookRepository.findByTitle(TEST_BOOK_TO_ADD_TITLE);
        assertTrue(newlyCreatedBookOpt.isPresent());

        BookEntity newlyCreatedBook = newlyCreatedBookOpt.get();

        assertEquals("title", newlyCreatedBook.getTitle());
        assertEquals("description", newlyCreatedBook.getDescription());
        assertEquals(2020, newlyCreatedBook.getYear());

        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "ADMIN")
    void test_AddBookInvalidParams_RedirectsToAddForm() throws Exception {
        mockMvc
                .perform(post("/admin/add-book")

                        .param("title", TEST_BOOK_TO_ADD_TITLE)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/add-book"));
    }


    @Test
    void test_GetBookDetailsInvalidId_Throws() throws Exception {
        mockMvc
                .perform(get("/admin/edit-book/9999999"))
                .andExpect(status().is(302));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "ADMIN")
    void test_GetUpdateBookForm_OpensForm() throws Exception {

        BookEntity bookToEdit = testBooks.get(0);

        mockMvc
                .perform(get("/admin/edit-book/" + bookToEdit.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/edit-book/"));

    }


    private List<BookEntity> initBooks() {

        BookEntity book1 = new BookEntity();
        book1.setTitle("TestBook1");
        book1.setYear(2020);
        book1.setPrice(BigDecimal.valueOf(5));
        book1.setCategory(testCategory);
        book1.setAuthor(testAuthor);

        testBooks.add(book1);
        bookRepository.save(book1);

        BookEntity book2 = new BookEntity();
        book2.setTitle("TestBook2");
        book2.setYear(2021);
        book2.setPrice(BigDecimal.valueOf(8));
        book2.setCategory(testCategory);
        book2.setAuthor(testAuthor);


        testBooks.add(book2);
        bookRepository.save(book2);


        BookEntity book3 = new BookEntity();
        book3.setTitle("TestBook3");
        book3.setYear(2019);
        book3.setPrice(BigDecimal.valueOf(10));
        book3.setCategory(testCategory);
        book3.setAuthor(testAuthor);
        testBooks.add(book3);
        bookRepository.save(book3);


        return List.of(book1, book2, book3);
    }

    private CategoryEntity initCategory() {
        CategoryEntity category = new CategoryEntity();
        category.setCategory("BIOGRAPHY");
        categoryRepository.save(category);
        return category;

    }

    private AuthorEntity initAuthor() {

        AuthorEntity author = new AuthorEntity();
        author.setFullName("MATT GREEN");
        authorRepository.save(author);
        return author;
    }

    private UserEntity initTestUser() {
        UserEntity testUser = new UserEntity();
        testUser.setFirstName("user");
        testUser.setLastName("user");
        testUser.setUsername("testUser");
        testUser.setEmail("user@abg.bg");
        testUser.setPassword("test");
        testUser.setRoles(Set.of(adminRole));

        userRepository.save(testUser);

        return testUser;
    }

    private UserRoleEntity initAdminRole() {

        UserRoleEntity adminRole = new UserRoleEntity();
        adminRole.setRole(UserRoleEnum.ADMIN);
        userRoleRepository.save(adminRole);

        return adminRole;
    }

    private ImageEntity initImage() {

        ImageEntity image = new ImageEntity();
        image.setUrl("newImage");
        imageRepository.save(image);
        return image;
    }


}