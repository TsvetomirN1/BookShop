package finalproject.bookshop.web.exceptions;

public class BookNotFoundException extends RuntimeException{


    public BookNotFoundException(Long id) {

        super("Book id not found : " + id);
    }
}
