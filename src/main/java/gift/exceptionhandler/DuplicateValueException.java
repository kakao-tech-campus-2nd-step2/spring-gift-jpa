package gift.exceptionhandler;

public class DuplicateValueException extends RuntimeException {

    public DuplicateValueException(String message) {
        super(message);
    }
}
