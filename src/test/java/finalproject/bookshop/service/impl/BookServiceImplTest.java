package finalproject.bookshop.service.impl;

import finalproject.bookshop.model.entity.*;
import finalproject.bookshop.model.entity.enums.CategoryEnum;
import finalproject.bookshop.model.entity.enums.UserRoleEnum;
import finalproject.bookshop.model.view.BooksViewModel;
import finalproject.bookshop.repository.*;
import finalproject.bookshop.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {


    private UserEntity testUser;
    private CategoryEntity testCategory;
    private AuthorEntity testAuthor;
    private List<BookEntity> testBooks;


    private BookService bookServiceToTest;


    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private BookRepository mockBookRepository;

    @Mock
    private ImageRepository mockImageRepository;

    @Mock
    private CategoryRepository mockCategoryRepository;


    @Mock
    private AuthorRepository mockAuthorRepository;

    @Mock
    private ModelMapper mockModelMapper;


    @BeforeEach
    void setUp() {

        testBooks = initBooks();
        testUser = initTestUser();
        testCategory = initCategory();
        testAuthor = initAuthor();
    }


    @Test
    void testAddBook() {

        Mockito.when(mockBookRepository.findAll())
                .thenReturn(testBooks);

        Mockito.when(mockModelMapper.map(testBooks.get(0), BooksViewModel.class))
                .thenReturn(new BooksViewModel().setTitle(testBooks.get(0).getTitle()));

        Mockito.when(mockModelMapper.map(testBooks.get(1), BooksViewModel.class))
                .thenReturn(new BooksViewModel().setTitle(testBooks.get(1).getTitle()));

        Mockito.when(mockModelMapper.map(testBooks.get(2), BooksViewModel.class))
                .thenReturn(new BooksViewModel().setTitle(testBooks.get(2).getTitle()));


        String actualTitles = bookServiceToTest.getAllBooks()
                .stream()
                .map(BooksViewModel::getTitle)
                .collect(Collectors.joining(", "));

        String expectedTitles = "TestBook1, TestBook2, TestBook3";

        assertEquals(expectedTitles, actualTitles);

    }

//    @Test
//    void getAllBooks() {
//    }
//
//    @Test
//    void findBookByID() {
//    }
//
//    @Test
//    void updateBook() {
//    }
//
//    @Test
//    void getBookById() {
//    }
//
//    @Test
//    void deleteBook() {
//    }


    private UserRoleEntity initUserRole() {

        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setRole(UserRoleEnum.USER);
        return userRole;
    }

    private UserRoleEntity initAdminRole() {
        UserRoleEntity adminRole = new UserRoleEntity();
        adminRole.setRole(UserRoleEnum.ADMIN);
        return adminRole;
    }

    private UserEntity initTestUser() {
        UserEntity testUser = new UserEntity();
        testUser.setFirstName("Admin");
        testUser.setLastName("Adminov");
        testUser.setUsername("adminuser");
        testUser.setEmail("admin@admin.bg");
        testUser.setPassword("12345");
        testUser.setRoles(Set.of(initAdminRole()));
        return testUser;
    }

    private List<BookEntity> initBooks() {
        BookEntity book1 = new BookEntity();
        book1.setTitle("TestBook1");
        book1.setYear(2020);
        book1.setPrice(BigDecimal.valueOf(5));
        book1.setCategory(initCategory());
        book1.setAuthor(testAuthor);
        testBooks.add(book1);

        BookEntity book2 = new BookEntity();
        book2.setTitle("TestBook2");
        book2.setYear(2021);
        book2.setPrice(BigDecimal.valueOf(8));
        book2.setCategory(initCategory());
        book2.setAuthor(testAuthor);
        testBooks.add(book2);

        BookEntity book3 = new BookEntity();
        book3.setTitle("TestBook3");
        book3.setYear(2019);
        book3.setPrice(BigDecimal.valueOf(10));
        book3.setCategory(initCategory());
        book3.setAuthor(testAuthor);
        testBooks.add(book3);

        return List.of(book1, book2, book3);
    }

    private CategoryEntity initCategory() {

        CategoryEntity category = new CategoryEntity();
        category.setCategory("HISTORY");
        return category;
    }


    private AuthorEntity initAuthor() {
        AuthorEntity author = new AuthorEntity();
        author.setFullName("MATT GREEN");
        return author;
    }
}