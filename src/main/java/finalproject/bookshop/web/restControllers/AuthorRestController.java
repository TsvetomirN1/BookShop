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

