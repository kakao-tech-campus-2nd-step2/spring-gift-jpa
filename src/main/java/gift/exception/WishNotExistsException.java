package gift.exception;

public class WishNotExistsException extends RuntimeException {

    public WishNotExistsException() {
        super("wish not exists");
    }
}
