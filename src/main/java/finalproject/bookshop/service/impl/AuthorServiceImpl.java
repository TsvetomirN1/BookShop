package finalproject.bookshop.service.impl;

import finalproject.bookshop.model.entity.AuthorEntity;
import finalproject.bookshop.model.entity.BookEntity;
import finalproject.bookshop.model.service.BookServiceModel;
import finalproject.bookshop.model.view.AuthorViewModel;
import finalproject.bookshop.repository.AuthorRepository;
import finalproject.bookshop.service.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initAuthors() {

        if (authorRepository.count() != 0) {
            return;
        }

        AuthorEntity matt = new AuthorEntity();
        matt.setId(matt.getId());
        matt.setFullName("MATT GREEN");
        authorRepository.save(matt);


        AuthorEntity lauren = new AuthorEntity();
        lauren.setId(lauren.getId());
        lauren.setFullName("LAUREN HILL");
        authorRepository.save(lauren);

    }

    @Override
    public AuthorEntity findByName(String fullName) {
        return authorRepository.findTopByFullName(fullName)
                .orElse(null);

    }

    @Override
    public void addAuthor(BookServiceModel bookAddServiceModel) {

        AuthorEntity author = new AuthorEntity();
        author.setId(bookAddServiceModel.getId());
        author.setFullName((bookAddServiceModel.getAuthor()));
        authorRepository.save(author);

    }

    @Override
    public List<AuthorViewModel> getAllAuthors() {

        return authorRepository.
                findAll().
                stream().
                map(this::asAuthor).
                collect(Collectors.toList());
    }

    private AuthorViewModel asAuthor(AuthorEntity author) {

        AuthorViewModel authorViewModel = modelMapper.map(author, AuthorViewModel.class);
        authorViewModel.setFullName(author.getFullName());

        authorViewModel.setBooks(author.getBooks()
                .stream()
                .map(BookEntity::getTitle).collect(Collectors.toList()));

        return authorViewModel;
    }
}


