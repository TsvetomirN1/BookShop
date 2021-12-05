package finalproject.bookshop.model.entity;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wishlist")
public class WishlistEntity extends BaseEntity {

    private BookEntity book;
    private UserEntity customer;

    public WishlistEntity() {
    }

    @ManyToOne(optional = false)
    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    @ManyToOne(optional = false)
    public UserEntity getCustomer() {
        return customer;
    }

    public void setCustomer(UserEntity customer) {
        this.customer = customer;
    }
}
