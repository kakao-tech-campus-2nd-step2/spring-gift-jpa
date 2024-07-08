package gift.exceptionhandler;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}