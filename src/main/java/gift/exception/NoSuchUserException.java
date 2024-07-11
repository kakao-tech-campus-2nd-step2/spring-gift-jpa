package gift.exception;

import java.util.NoSuchElementException;

public class NoSuchUserException extends NoSuchElementException {
    public NoSuchUserException() {
        super("No such user");
    }

    public NoSuchUserException(String message) {
        super(message);
    }

    public NoSuchUserException(Throwable cause) {
        super(cause);
    }

    public NoSuchUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
