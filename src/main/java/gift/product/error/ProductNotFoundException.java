package gift.product.error;

import gift.error.CustomException;
import gift.error.ErrorCode;

public class ProductNotFoundException extends CustomException {

    public ProductNotFoundException() {
        super(ErrorCode.PRODUCT_NOT_FOUND);
    }

}
