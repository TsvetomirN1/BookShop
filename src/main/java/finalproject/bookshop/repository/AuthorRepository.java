package finalproject.bookshop.repository;

import finalproject.bookshop.model.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity,Long> {

    Optional<AuthorEntity> findTopByFullName(String fullName);

}
