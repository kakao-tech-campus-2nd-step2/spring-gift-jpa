package gift.exception.BadRequestExceptions;

public class BlankContentException extends BadRequestException {

    public BlankContentException() {
        super();
    }

    public BlankContentException(String message) {
        super(message);
    }
}
