package finalproject.bookshop.repository;

import finalproject.bookshop.model.entity.BookEntity;
import finalproject.bookshop.model.view.BooksViewModel;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findByTitle(String title);
}
