package gift.exception;

import gift.constant.ErrorMessage;

public class ProductNotInWishlistException extends GiftException {

    public ProductNotInWishlistException() {
        super(ErrorMessage.PRODUCT_NOT_IN_WISHLIST);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }

}
