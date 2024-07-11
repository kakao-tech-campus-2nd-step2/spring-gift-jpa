package gift.exception;

import gift.constant.ErrorMessage;

public class ProductNotFoundException extends GiftException {

    public ProductNotFoundException() {
        super(ErrorMessage.PRODUCT_NOT_FOUND.getMessage());
    }

    @Override
    public int getStatusCode() {
        return 404;
    }

}
