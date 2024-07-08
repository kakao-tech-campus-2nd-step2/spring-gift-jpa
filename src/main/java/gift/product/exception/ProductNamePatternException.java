package gift.product.exception;

import gift.global.exception.BusinessException;
import gift.global.response.ErrorCode;

public class ProductNamePatternException extends BusinessException {
    public ProductNamePatternException() {
        super(ErrorCode.PRODUCT_NAME_PATTER_ERROR);
    }
}
