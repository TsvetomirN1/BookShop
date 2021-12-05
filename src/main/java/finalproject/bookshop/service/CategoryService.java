package finalproject.bookshop.service;

import finalproject.bookshop.model.entity.CategoryEntity;
import finalproject.bookshop.model.service.BookServiceModel;

import java.util.List;

public interface CategoryService {

//    void initCategories();

    List<CategoryEntity> getAll();

    void addCategory(BookServiceModel bookAddServiceModel);

    CategoryEntity findByCategory(String category);
}
