package finalproject.bookshop.service;


import finalproject.bookshop.model.entity.AuthorEntity;
import finalproject.bookshop.model.service.BookServiceModel;
import finalproject.bookshop.model.view.AuthorViewModel;

import java.util.List;


public interface AuthorService {

    void initAuthors();

    AuthorEntity findByName(String fullName);

    void addAuthor(BookServiceModel bookAddServiceModel);

    List<AuthorViewModel> getAllAuthors();




}
