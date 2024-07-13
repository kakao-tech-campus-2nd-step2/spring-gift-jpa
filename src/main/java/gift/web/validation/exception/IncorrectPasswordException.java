package gift.web.validation.exception;

import gift.web.validation.exception.code.ErrorCode;

public class IncorrectPasswordException extends InvalidCredentialsException {

    public IncorrectPasswordException() {
        super(ErrorCode.INCORRECT_PASSWORD.getDescription());
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }

}
