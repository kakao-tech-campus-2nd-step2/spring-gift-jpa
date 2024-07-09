package gift.exception.BadRequestExceptions;

public class EmailAlreadyHereException extends BadRequestException {
    public EmailAlreadyHereException() { super(); }
    public EmailAlreadyHereException(String message) { super(message); }
}
