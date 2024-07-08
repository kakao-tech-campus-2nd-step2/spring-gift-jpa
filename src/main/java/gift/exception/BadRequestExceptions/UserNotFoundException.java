package gift.exception.BadRequestExceptions;

public class UserNotFoundException extends BadRequestException {
    public UserNotFoundException() {super();}
    public UserNotFoundException(String message) {super(message);}
}
