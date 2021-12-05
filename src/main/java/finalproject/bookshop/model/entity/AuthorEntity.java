package finalproject.bookshop.model.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name = "authors")
public class AuthorEntity extends BaseEntity {

    private String fullName;
    private List<BookEntity> books = new LinkedList<>();


    public AuthorEntity() {
    }
    @Column()
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @OneToMany(mappedBy = "author")
    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }


}
