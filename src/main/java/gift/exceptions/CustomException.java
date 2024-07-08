package gift.exceptions;

public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }

    public String invalidNameException() {
        return getMessage();
    }

    public String invalidUserException() {
        return getMessage();
    }

    public String userNotFoundException() {
        return getMessage();
    }
}
