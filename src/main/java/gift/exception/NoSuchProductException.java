package gift.exception;

import java.util.NoSuchElementException;

public class NoSuchProductException extends NoSuchElementException {
    public NoSuchProductException() {
        super("No such product");
    }

    public NoSuchProductException(String message) {
        super(message);
    }

    public NoSuchProductException(Throwable cause) {
        super(cause);
    }

    public NoSuchProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
