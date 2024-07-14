package gift.exception;

import java.util.NoSuchElementException;

public class NoSuchFieldException extends NoSuchElementException {
    public NoSuchFieldException() {
        super("No such field corresponding to sort type");
    }

    public NoSuchFieldException(String message) {
        super(message);
    }

    public NoSuchFieldException(Throwable cause) {
        super(cause);
    }

    public NoSuchFieldException(String message, Throwable cause) {
        super(message, cause);
    }
}
