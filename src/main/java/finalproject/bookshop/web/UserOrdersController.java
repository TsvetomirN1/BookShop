package finalproject.bookshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserOrdersController {

    @GetMapping("/user/orders")
    public String getOrders() {
        return "/user/orders";
    }
}
