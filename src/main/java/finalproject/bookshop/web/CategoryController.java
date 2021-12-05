package finalproject.bookshop.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {

    @GetMapping("/categories/all")
    public String categories() {
        return "categories";
    }
}
