package gift.web.validation.exception.client;

import static gift.web.validation.exception.code.ErrorCode.UNAUTHORIZED_INVALID_CREDENTIALS;

import gift.web.validation.exception.code.ErrorCode;

public class InvalidCredentialsException extends ClientException {

    private static final String ERROR_MESSAGE = "유효하지 않은 신원 정보입니다.";

    public InvalidCredentialsException() {
        super(ERROR_MESSAGE);
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCredentialsException(Throwable cause) {
        super(cause);
    }

    @Override
    public ErrorCode getErrorCode() {
        return UNAUTHORIZED_INVALID_CREDENTIALS;
    }

}
