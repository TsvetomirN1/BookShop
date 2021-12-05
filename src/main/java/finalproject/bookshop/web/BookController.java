package finalproject.bookshop.web;


import finalproject.bookshop.model.binding.BookAddBindingModel;
import finalproject.bookshop.model.binding.BookEditBindingModel;
import finalproject.bookshop.model.service.BookServiceModel;
import finalproject.bookshop.model.view.BooksViewModel;
import finalproject.bookshop.service.BookService;
import finalproject.bookshop.web.exceptions.BookNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class BookController {

    private final BookService bookService;
    private final ModelMapper modelMapper;

    public BookController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/admin/add-book")
    public String addBook() {
        return "/admin/add-book";
    }

    @GetMapping("/admin/all-books")
    public String allBook() {
        return "/admin/all-books";
    }

    @PostMapping("/admin/add-book")
    public String addBookConfirm(@Valid BookAddBindingModel bookAddBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("bookAddBindingModel", bookAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.bookAddBindingModel", bindingResult);

            return "redirect:/admin/add-book";
        }

        BookServiceModel bookAddServiceModel =
                modelMapper.map(bookAddBindingModel, BookServiceModel.class);

        bookService.addBook(bookAddServiceModel);

        return "redirect:/";
    }


    @ModelAttribute("bookAddBindingModel")
    public BookAddBindingModel bookAddBindingModel() {
        return new BookAddBindingModel();

    }


    @GetMapping("/admin/edit-book/{id}")
    public String editBook(@PathVariable Long id, Model model) {

        BookEditBindingModel bookEditBindingModel = bookService
                .findBookByID(id)
                .orElseThrow(() ->
                        new BookNotFoundException(id));

        model.addAttribute("bookEditBindingModel", bookEditBindingModel);


        return "/admin/edit-book";
    }

    @GetMapping("/admin/edit/{id}/errors")
    public String editConfirmErrors(@PathVariable Long id, Model model) {

        model.addAttribute("error", BookNotFoundException.class);

        return "redirect:admin/edit-book/" + id;
    }

    @Transactional
    @PatchMapping("/admin/edit-book/{id}")
    public String editConfirm(@PathVariable Long id,
                              @Valid BookEditBindingModel bookEditBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("bookEditBindingModel", bookEditBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.bookEditBindingModel", bindingResult);

            return "redirect:admin/edit-book/" + id + "/error";
        }

        BookServiceModel bookServiceModel = modelMapper
                .map(bookEditBindingModel, BookServiceModel.class);
        bookServiceModel.setId(id);

        bookService.updateBook(bookServiceModel);

        redirectAttributes.addFlashAttribute("You update the book successfully", true);
        return "redirect:/";
    }


    @ModelAttribute("bookServiceModel")
    public BookServiceModel bookServiceModel() {
        return new BookServiceModel();

    }

    @ModelAttribute("bookViewModel")
    public BooksViewModel booksViewModel() {
        return new BooksViewModel();

    }
}


