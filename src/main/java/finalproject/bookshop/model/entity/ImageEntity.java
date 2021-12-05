package finalproject.bookshop.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Member;

@Entity
@Table(name = "images")
public class ImageEntity extends BaseEntity {

    private String url;
    private BookEntity book;

    public ImageEntity() {
    }


    @OneToOne(mappedBy = "image")
    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    @Column()
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

