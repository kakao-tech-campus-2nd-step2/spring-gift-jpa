package gift.exception;

import gift.constant.ErrorMessage;

public class ProductAlreadyInWishlistException extends GiftException {

    public ProductAlreadyInWishlistException() {
        super(ErrorMessage.PRODUCT_ALREADY_IN_WISHLIST);
    }

    @Override
    public int getStatusCode() {
        return 409;
    }

}
