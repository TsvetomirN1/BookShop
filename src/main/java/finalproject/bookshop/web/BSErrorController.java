package finalproject.bookshop.web;

import finalproject.bookshop.web.exceptions.ObjectNotFoundException;
import finalproject.bookshop.web.exceptions.ServerErrorException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class BSErrorController implements ErrorController {

    @RequestMapping("/error")
    public Object handleError(HttpServletRequest request) {

        Object error = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (error != null) {
            int statusCode = Integer.parseInt(error.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return ObjectNotFoundException.class;
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return ServerErrorException.class;
            }
        }
        return "error";
    }
}
