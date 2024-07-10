package gift.wishlist.error;

import gift.error.CustomException;
import gift.error.ErrorCode;

public class WishAlreadyExistsException extends CustomException {

    public WishAlreadyExistsException() {
        super(ErrorCode.WISH_ALREADY_EXISTS);
    }

}
