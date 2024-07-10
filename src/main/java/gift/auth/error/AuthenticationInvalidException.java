package gift.auth.error;

import gift.error.CustomException;
import gift.error.ErrorCode;

public class AuthenticationInvalidException extends CustomException {

    public AuthenticationInvalidException() {
        super(ErrorCode.AUTHENTICATION_INVALID);
    }

}
