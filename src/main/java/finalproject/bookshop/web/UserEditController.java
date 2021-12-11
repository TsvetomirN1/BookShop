package finalproject.bookshop.web;

import finalproject.bookshop.model.binding.UserUpdateBindingModel;
import finalproject.bookshop.model.service.UserUpdateServiceModel;
import finalproject.bookshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserEditController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserEditController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @GetMapping("/user/details")
    public String editDetails(Principal principal, Model model) {

        Long id = userService.findLoggedUserByName(principal.getName()).getId();
        UserUpdateServiceModel userUpdateServiceModel = userService.findById(id);

        model.addAttribute("userUpdateBindingModel", modelMapper.map(userUpdateServiceModel, UserUpdateBindingModel.class));
        return "/user/details";
    }

    @PostMapping("/user/details")
    public String editDetailsConfirm(@Valid UserUpdateBindingModel userUpdateBindingModel,
                                     BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                     Principal principal) {

        Long id = userService.findLoggedUserByName(principal.getName()).getId();

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userUpdateBindingModel", userUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userUpdateBindingModel", bindingResult);

            return "redirect:/user/details";
        }

        UserUpdateServiceModel userUpdateServiceModel = modelMapper.map(userUpdateBindingModel,UserUpdateServiceModel.class) ;
        userService.updateUser(userUpdateServiceModel, id);

        redirectAttributes.addFlashAttribute("message", "Your account details have been updated!");
        return "redirect:/";
    }
}


