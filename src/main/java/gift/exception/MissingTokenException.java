package gift.exception;

import gift.constant.ErrorMessage;

public class MissingTokenException extends GiftException {

    public MissingTokenException() {
        super(ErrorMessage.MISSING_TOKEN);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }

}
