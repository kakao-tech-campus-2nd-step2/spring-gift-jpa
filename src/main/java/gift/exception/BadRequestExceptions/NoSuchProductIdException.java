package gift.exception.BadRequestExceptions;

public class NoSuchProductIdException extends BadRequestException {

    priavte NoSuchProductIdException() {
        super();
    }

    public NoSuchProductIdException(String message) {
        super(message);
    }

}
