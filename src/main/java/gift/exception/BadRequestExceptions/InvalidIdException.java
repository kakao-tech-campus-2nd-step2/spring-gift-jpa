package gift.exception.BadRequestExceptions;

public class InvalidIdException extends BadRequestException {
    public InvalidIdException() {
        super();
    }

    public InvalidIdException(String message) {
        super(message);
    }
}
