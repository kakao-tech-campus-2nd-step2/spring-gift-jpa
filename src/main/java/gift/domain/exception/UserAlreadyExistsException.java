package gift.domain.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("Your email already registered. Retry with other one.");
    }
}
