package finalproject.bookshop.web;

import finalproject.bookshop.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class UserLoginsController {

    private final RequestService requestService;

    public UserLoginsController(RequestService requestService) {
        this.requestService = requestService;
    }

    @RequestMapping("/user/logins")
    public ModelAndView logins(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("user/logins");
        String clientIp = requestService.getClientIp(request);
        model.addObject("clientIp", clientIp);
        return model;
    }
}

