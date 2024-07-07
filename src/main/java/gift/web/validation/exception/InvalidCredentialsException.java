package gift.web.validation.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("인증에 실패하였습니다.");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
