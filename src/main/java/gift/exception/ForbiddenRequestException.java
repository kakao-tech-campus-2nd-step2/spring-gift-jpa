package gift.exception;

public class ForbiddenRequestException extends RuntimeException {
    public ForbiddenRequestException() {
        super("the request is forbidden");
    }

    public ForbiddenRequestException(String message) {
        super(message);
    }

    public ForbiddenRequestException(Throwable cause) {
        super(cause);
    }

    public ForbiddenRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
