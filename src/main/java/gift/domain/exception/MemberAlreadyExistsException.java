package gift.domain.exception;

public class MemberAlreadyExistsException extends RuntimeException {

    public MemberAlreadyExistsException() {
        super("Your email already registered. Retry with other one.");
    }
}
