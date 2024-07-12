package gift.exception;

public class WishAlreadyExistsException extends RuntimeException {

    public WishAlreadyExistsException() {
        super("Wish already exists");
    }

}
