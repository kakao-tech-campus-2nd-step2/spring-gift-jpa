package gift.web.validation.exception;

import gift.web.validation.exception.code.ErrorCode;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super(ErrorCode.UNAUTHORIZED_INVALID_CREDENTIALS.getDescription());
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
