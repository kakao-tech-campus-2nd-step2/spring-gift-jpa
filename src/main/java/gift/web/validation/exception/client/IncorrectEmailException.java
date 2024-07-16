package gift.web.validation.exception.client;

import static gift.web.validation.exception.code.ErrorCode.INCORRECT_EMAIL;

import gift.web.validation.exception.code.ErrorCode;

public class IncorrectEmailException extends ClientException {

    private static final String ERROR_MESSAGE = "%s 는 잘못된 이메일입니다.";

    public IncorrectEmailException(String email) {
        super(ERROR_MESSAGE.formatted(email));
    }

    public IncorrectEmailException(String email, Throwable cause) {
        super(ERROR_MESSAGE.formatted(email), cause);
    }

    public IncorrectEmailException(Throwable cause) {
        super(cause);
    }

    @Override
    public ErrorCode getErrorCode() {
        return INCORRECT_EMAIL;
    }

}
