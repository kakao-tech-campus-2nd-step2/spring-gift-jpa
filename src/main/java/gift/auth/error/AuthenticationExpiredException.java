package gift.auth.error;

import gift.error.CustomException;
import gift.error.ErrorCode;

public class AuthenticationExpiredException extends CustomException {

    public AuthenticationExpiredException() {
        super(ErrorCode.AUTHENTICATION_EXPIRED);
    }

}
