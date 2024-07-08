package gift.exception.CustomException;

import gift.exception.ErrorCode;

public class ItemNotFoundException extends CustomException {

    public ItemNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
