package finalproject.bookshop.web.restControllers;

import finalproject.bookshop.model.service.BookServiceModel;
import finalproject.bookshop.model.view.BooksViewModel;
import finalproject.bookshop.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<BooksViewModel>> getAllBooks() {
        List<BooksViewModel> allBooks = bookService.getAllBooks();

        return ResponseEntity.
                ok(allBooks);
    }


        @GetMapping("/{id}")
        public ResponseEntity<BooksViewModel> getBookById(@PathVariable("id") Long id) {
            Optional<BooksViewModel> book = bookService.getBookById(id);

            if (book.isEmpty()) {
                return ResponseEntity.
                        notFound().
                        build();
            } else {
                return ResponseEntity.
                        ok(book.get());
            }
        }


        @DeleteMapping("/{id}")
        public ResponseEntity<BookServiceModel> deleteBook(@PathVariable("id") Long id) {
            bookService.deleteBook(id);

            return ResponseEntity.
                    noContent().
                    build();
        }

}
