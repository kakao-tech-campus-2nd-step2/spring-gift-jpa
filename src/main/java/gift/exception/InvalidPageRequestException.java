package gift.exception;

public class InvalidPageRequestException extends RuntimeException {
    public InvalidPageRequestException() {
        super("page request is invalid. please check your request parameters.");
    }

    public InvalidPageRequestException(String message) {
        super(message);
    }

    public InvalidPageRequestException(Throwable cause) {
        super(cause);
    }

    public InvalidPageRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
