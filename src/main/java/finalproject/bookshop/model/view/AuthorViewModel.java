package finalproject.bookshop.model.view;


import java.util.List;

public class AuthorViewModel {

    private String fullName;
    private List<String> books;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<String> getBooks() {
        return books;
    }

    public void setBooks(List<String> books) {
        this.books = books;
    }
}
