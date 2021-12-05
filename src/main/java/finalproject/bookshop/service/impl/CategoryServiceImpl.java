package finalproject.bookshop.service.impl;

import finalproject.bookshop.model.entity.CategoryEntity;
import finalproject.bookshop.model.service.BookServiceModel;
import finalproject.bookshop.repository.CategoryRepository;
import finalproject.bookshop.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<CategoryEntity> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void addCategory(BookServiceModel bookAddServiceModel) {
        CategoryEntity category = new CategoryEntity();
        category.setId(bookAddServiceModel.getId());
        category.setCategory(bookAddServiceModel.getCategory());
        categoryRepository.save(category);
    }

    @Override
    public CategoryEntity findByCategory(String category) {
        return categoryRepository.findTopByCategory(category)
                .orElse(new CategoryEntity());
    }
}
