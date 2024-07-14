package gift.domain.exception;

public class MemberIncorrectLoginInfoException extends RuntimeException {

    public MemberIncorrectLoginInfoException() {
        super("Incorrect your email or password. Try again.");
    }
}
