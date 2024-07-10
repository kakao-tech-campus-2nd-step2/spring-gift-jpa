package gift.auth.error;

import gift.error.CustomException;
import gift.error.ErrorCode;

public class AuthenticationFailedException extends CustomException {

    public AuthenticationFailedException() {
        super(ErrorCode.AUTHENTICATION_FAILED);
    }

}
