package gift.exception;

import gift.constant.ErrorMessage;

public class InvalidTokenException extends GiftException {

    public InvalidTokenException() {
        super(ErrorMessage.INVALID_TOKEN.getMessage());
    }

    @Override
    public int getStatusCode() {
        return 401;
    }

}
