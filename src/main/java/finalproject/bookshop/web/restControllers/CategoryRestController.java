package finalproject.bookshop.web.restControllers;
import finalproject.bookshop.model.entity.CategoryEntity;
import finalproject.bookshop.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/categories")
public class CategoryRestController {

    private final CategoryService categoryService;


    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryEntity>> getAllCategories() {
        List<CategoryEntity> allCategories = categoryService.getAll();

        return ResponseEntity.
                ok(allCategories);
    }
}
