package finalproject.bookshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserBooksController {

    @GetMapping("/user/books")
    public String about() {return "/user/books";}
}
