package gift.exception;

import java.util.NoSuchElementException;

public class NoSuchMemberException extends NoSuchElementException {
    public NoSuchMemberException() {
        super("No such user");
    }

    public NoSuchMemberException(String message) {
        super(message);
    }

    public NoSuchMemberException(Throwable cause) {
        super(cause);
    }

    public NoSuchMemberException(String message, Throwable cause) {
        super(message, cause);
    }
}
