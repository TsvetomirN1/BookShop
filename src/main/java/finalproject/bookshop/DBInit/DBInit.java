package finalproject.bookshop.DBInit;

import finalproject.bookshop.service.AuthorService;
import finalproject.bookshop.service.CategoryService;
import finalproject.bookshop.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {
    private final UserService userService;
    private final CategoryService categoryService;
    private final AuthorService authorService;

    public DBInit(UserService userService, CategoryService categoryService, AuthorService authorService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.authorService = authorService;
    }

    @Override
    public void run(String... args) throws Exception {

        userService.initializeUsersAndRoles();
////        categoryService.initCategories();
//        authorService.initAuthors();
    }
}
