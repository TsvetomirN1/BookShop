package finalproject.bookshop.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

    private BigDecimal price;
    private UserEntity user;
    private List<OrderedItemsEntity> orderedBooks = new ArrayList<>();

    public OrderEntity() {
    }
    @Column(nullable = false)
    @PositiveOrZero
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @ManyToOne
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    public List<OrderedItemsEntity> getOrderedBooks() {
        return orderedBooks;
    }

    public void setOrderedBooks(List<OrderedItemsEntity> orderedBooks) {
        this.orderedBooks = orderedBooks;
    }
}
