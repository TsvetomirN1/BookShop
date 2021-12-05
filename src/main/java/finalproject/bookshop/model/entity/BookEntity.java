package finalproject.bookshop.model.entity;


import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@NamedEntityGraph(
        name = "bookEntity",
        attributeNodes = {
//                @NamedAttributeNode("picture"),
//                @NamedAttributeNode("categories"),
                @NamedAttributeNode("author"),
//                @NamedAttributeNode("creator")
        }
)
@Entity
@Table(name = "books")
public class BookEntity extends BaseEntity {

    private String title;
    private String description;
    private BigDecimal price;
    private Integer year;
    private ImageEntity image;
    private CategoryEntity category;
    private AuthorEntity author;

    public BookEntity() {
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Lob
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(nullable = false)
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    public ImageEntity getImage() {
        return image;
    }

    public void setImage(ImageEntity image) {
        this.image = image;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }


}


