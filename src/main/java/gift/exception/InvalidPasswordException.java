package gift.exception;

import javax.security.sasl.AuthenticationException;

public class InvalidPasswordException extends AuthenticationException {

    public InvalidPasswordException() {
        super("Invalid Password");
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
