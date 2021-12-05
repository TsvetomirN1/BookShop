package finalproject.bookshop.web.restControllers;


import finalproject.bookshop.model.view.AuthorViewModel;
import finalproject.bookshop.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/authors")
public class AuthorRestController {

    private final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AuthorViewModel>> getAllAuthors() {
        List<AuthorViewModel> allAuthors = authorService.getAllAuthors();

        return ResponseEntity.
                ok(allAuthors);
    }
}


//TODO implement the methods


//        @GetMapping("/pageable")
//        public ResponseEntity<Page<BookDTO>> getBooks(
//                @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
//                @RequestParam(name = "pageSize", defaultValue = "3") Integer pageSize,
//                @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
//
//            return ResponseEntity.ok(
//                    booksService.getBooks(pageNo, pageSize, sortBy));
//
//        }


//        @GetMapping("/{id}")
//        public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long id) {
//            Optional<BookDTO> book = booksService.getBookById(id);
//
//            if (book.isEmpty()) {
//                return ResponseEntity.
//                        notFound().
//                        build();
//            } else {
//                return ResponseEntity.
//                        ok(book.get());
//            }
//        }



//        @DeleteMapping("/{id}")
//        public ResponseEntity<BookDTO> deleteBook(@PathVariable("id") Long id) {
//            booksService.
//                    deleteBook(id);
//
//            return ResponseEntity.
//                    noContent().
//                    build();
//        }



//        @PutMapping("/{id}")
//        public ResponseEntity<BookDTO> update(@PathVariable("id") long bookId,
//                                              @RequestBody BookDTO bookDTO) {
//            Long updatedBookId = booksService.updateBook(bookDTO.setId(bookId));
//            return updatedBookId != null ?
//                    ResponseEntity.noContent().build()
//                    : ResponseEntity.notFound().build();
//        }
//

//        //My mistake :P
//        @PostMapping()
//        public ResponseEntity<BookDTO> create(
//                @RequestBody BookDTO bookDTO,
//                UriComponentsBuilder builder) {
//            //http://localhost:8080/books/id
//            long bookId = booksService.createBook(bookDTO);
//
//            URI location = builder.path("/books/{id}").
//                    buildAndExpand(bookId).toUri();
//
//            return ResponseEntity.
//                    created(location).
//                    build();
//
//        }