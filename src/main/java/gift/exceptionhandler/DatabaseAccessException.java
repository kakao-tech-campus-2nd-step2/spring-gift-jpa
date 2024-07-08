package gift.exceptionhandler;

public class DatabaseAccessException extends RuntimeException {

    public DatabaseAccessException(String message) {
        super(message);
    }
}