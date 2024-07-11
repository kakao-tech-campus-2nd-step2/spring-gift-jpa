package gift.exception;

public class DuplicatedEmailException extends RuntimeException {

    public DuplicatedEmailException() {
        super("Email already exists");
    }

    public DuplicatedEmailException(String message) {
        super(message);
    }

    public DuplicatedEmailException(Throwable cause) {
        super(cause);
    }

    public DuplicatedEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
