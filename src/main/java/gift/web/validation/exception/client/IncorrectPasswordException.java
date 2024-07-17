package gift.web.validation.exception.client;

import static gift.web.validation.exception.code.ErrorCode.INCORRECT_PASSWORD;

import gift.web.validation.exception.code.ErrorCode;

public class IncorrectPasswordException extends ClientException {

    private static final String ERROR_MESSAGE = "비밀번호가 일치하지 않습니다.";

    public IncorrectPasswordException() {
        super(ERROR_MESSAGE);
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }

    public IncorrectPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectPasswordException(Throwable cause) {
        super(cause);
    }

    @Override
    public ErrorCode getErrorCode() {
        return INCORRECT_PASSWORD;
    }

}
