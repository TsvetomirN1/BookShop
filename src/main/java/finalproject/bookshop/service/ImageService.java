package finalproject.bookshop.service;

import finalproject.bookshop.model.entity.BookEntity;
import finalproject.bookshop.model.entity.ImageEntity;

import finalproject.bookshop.model.service.BookServiceModel;

import java.util.List;
import java.util.Optional;

public interface ImageService {

    void addImage(BookServiceModel bookAddServiceModel);

    ImageEntity findByUrl(String url);


    ImageEntity findByID(Long id);
}
