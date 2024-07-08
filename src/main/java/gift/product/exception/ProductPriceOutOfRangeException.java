package gift.product.exception;

import gift.global.exception.BusinessException;
import gift.global.response.ErrorCode;

public class ProductPriceOutOfRangeException extends BusinessException {
    public ProductPriceOutOfRangeException() {
        super(ErrorCode.PRODUCT_PRICE_OUT_OF_RANGE_ERROR);
    }
}
