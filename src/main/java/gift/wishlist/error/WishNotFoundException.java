package gift.wishlist.error;

import gift.error.CustomException;
import gift.error.ErrorCode;

public class WishNotFoundException extends CustomException {

    public WishNotFoundException() {
        super(ErrorCode.WISH_NOT_FOUND);
    }

}
