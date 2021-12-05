package finalproject.bookshop.web.exceptions;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(Long id) {

        super("Object id not found : " + id);
    }
}
