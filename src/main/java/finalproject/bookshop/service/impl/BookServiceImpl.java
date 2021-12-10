package finalproject.bookshop.service.impl;

import finalproject.bookshop.model.binding.BookEditBindingModel;
import finalproject.bookshop.model.entity.AuthorEntity;
import finalproject.bookshop.model.entity.BookEntity;
import finalproject.bookshop.model.service.BookServiceModel;
import finalproject.bookshop.model.view.BooksViewModel;
import finalproject.bookshop.repository.BookRepository;
import finalproject.bookshop.service.AuthorService;
import finalproject.bookshop.service.BookService;
import finalproject.bookshop.service.CategoryService;
import finalproject.bookshop.service.ImageService;
import finalproject.bookshop.web.exceptions.BookNotFoundException;
import finalproject.bookshop.web.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class BookServiceImpl implements BookService {


    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final ImageService imageService;
    private final ModelMapper modelMapper;


    public BookServiceImpl(BookRepository bookRepository, CategoryService categoryService, AuthorService authorService, ImageService imageService, ModelMapper modelMapper) {

        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.imageService = imageService;
        this.modelMapper = modelMapper;

    }

    @Override
    public void addBook(BookServiceModel bookAddServiceModel) {

        BookEntity book = modelMapper.map(bookAddServiceModel, BookEntity.class);

        categoryService.addCategory(bookAddServiceModel);
        authorService.addAuthor(bookAddServiceModel);
        imageService.addImage(bookAddServiceModel);

        book.setCategory(categoryService.findByCategory(bookAddServiceModel.getCategory()));
        book.setAuthor(authorService.findByName(bookAddServiceModel.getAuthor()));
        book.setImage(imageService.findByUrl(bookAddServiceModel.getImage()));

        bookRepository.save(book);
    }

    @Override
    public List<BooksViewModel> getAllBooks() {

        return bookRepository.
                findAll().
                stream().
                map(this::asBook).
                collect(Collectors.toList());

    }

    @Override
    public Optional<BookEditBindingModel> findBookByID(Long id) {
        return bookRepository
                .findById(id)
                .map(this::editBook);
    }


    private BookEditBindingModel editBook(BookEntity book) {
        BookEditBindingModel bookEditBindingModel = modelMapper.map(book, BookEditBindingModel.class);

        bookEditBindingModel.setAuthor(book.getAuthor().getFullName());
        bookEditBindingModel.setImage(book.getImage().getUrl());
        bookEditBindingModel.setCategory(book.getCategory().getCategory());
        return bookEditBindingModel;
    }

    @Transactional
    @Override
    public void updateBook(BookServiceModel bookservicemodel) {

        BookEntity bookEntity = bookRepository
                .findById(bookservicemodel.getId())
                .orElseThrow(() ->
                        new BookNotFoundException(bookservicemodel.getId()));

        categoryService.addCategory(bookservicemodel);
        authorService.addAuthor(bookservicemodel);
        imageService.addImage(bookservicemodel);

        AuthorEntity author = authorService.
                findByName(bookservicemodel.getAuthor());


        bookEntity.setId(bookservicemodel.getId());
        bookEntity.setTitle(bookservicemodel.getTitle());
        bookEntity.setYear(bookservicemodel.getYear());
        bookEntity.setDescription(bookservicemodel.getDescription());

        bookEntity.setCategory(categoryService.findByCategory(bookservicemodel.getCategory()));
        bookEntity.setAuthor(author);
        bookEntity.setImage(imageService.findByUrl(bookservicemodel.getImage()));
        bookRepository.save(bookEntity);

    }

    @Override
    public Optional<BooksViewModel> getBookById(Long id) {

        return bookRepository.findById(id)
                .map(this::asBook);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    private BooksViewModel asBook(BookEntity book) {
        BooksViewModel booksViewModel = modelMapper.map(book, BooksViewModel.class);
        booksViewModel.setAuthor(book.getAuthor().getFullName());
        booksViewModel.setImage(book.getImage().getUrl());
        return booksViewModel;
    }

}
