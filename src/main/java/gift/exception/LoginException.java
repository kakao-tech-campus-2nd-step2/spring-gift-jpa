package gift.exception;

import gift.constant.ErrorMessage;

public class LoginException extends GiftException {

    public LoginException() {
        super(ErrorMessage.LOGIN_FAILURE);
    }

    @Override
    public int getStatusCode() {
        return 403;
    }

}
