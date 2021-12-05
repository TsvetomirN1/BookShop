package finalproject.bookshop.service;

import finalproject.bookshop.model.binding.BookEditBindingModel;
import finalproject.bookshop.model.service.BookServiceModel;
import finalproject.bookshop.model.view.BooksViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BookService {

    void addBook(BookServiceModel bookAddServiceModel);

    List<BooksViewModel> getAllBooks();

    Optional<BookEditBindingModel> findBookByID(Long id);

    void updateBook(BookServiceModel bookServiceModel) ;

}
