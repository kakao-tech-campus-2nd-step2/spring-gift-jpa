package gift.web.validation.exception;

import gift.web.validation.exception.code.ErrorCode;

public class IncorrectEmailException extends InvalidCredentialsException {

    public IncorrectEmailException() {
        super(ErrorCode.INCORRECT_EMAIL.getDescription());
    }

    public IncorrectEmailException(String message) {
        super(message);
    }

}
