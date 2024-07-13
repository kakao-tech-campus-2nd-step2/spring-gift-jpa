package gift.exception;

import java.util.NoSuchElementException;

public class NoSuchWishException extends NoSuchElementException {
    public NoSuchWishException() {
        super("No such wish");
    }

    public NoSuchWishException(String message) {
        super(message);
    }

    public NoSuchWishException(Throwable cause) {
        super(cause);
    }

    public NoSuchWishException(String message, Throwable cause) {
        super(message, cause);
    }
}
