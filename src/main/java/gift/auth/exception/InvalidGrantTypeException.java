package gift.auth.exception;

import gift.common.exception.BusinessException;

public class InvalidGrantTypeException extends BusinessException {

    public static BusinessException EXCEPTION = new InvalidGrantTypeException();

    private InvalidGrantTypeException() {
        super(AuthErrorCode.INVALID_GRANT_TYPE);
    }
}
