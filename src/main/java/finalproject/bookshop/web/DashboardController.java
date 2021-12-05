package finalproject.bookshop.web;

import finalproject.bookshop.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {


    @GetMapping("admin/dashboard")
    public String dashboard(){
        return "admin/dashboard";
    }
}
