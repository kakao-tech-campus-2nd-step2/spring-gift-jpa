package gift.product.exception;

import gift.global.exception.BusinessException;
import gift.global.response.ErrorCode;

public class ProductNameLengthException extends BusinessException {
    public ProductNameLengthException() {
        super(ErrorCode.PRODUCT_NAME_LENGTH_ERROR);
    }
}
