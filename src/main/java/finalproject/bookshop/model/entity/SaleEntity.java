package finalproject.bookshop.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "sales")
public class SaleEntity extends BaseEntity {

    private BookEntity book;
    private UserEntity user;

    public SaleEntity() {
    }

    @ManyToOne
    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    @ManyToOne
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
