package gift.product.exception;

import gift.global.exception.BusinessException;
import gift.global.response.ErrorCode;

public class ProductNameContainsException extends BusinessException {
    public ProductNameContainsException() {
        super(ErrorCode.PRODUCT_NAME_CONTAINS_ERROR);
    }
}
