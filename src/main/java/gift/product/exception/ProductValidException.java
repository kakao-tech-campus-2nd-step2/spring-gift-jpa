package gift.product.exception;

import gift.global.exception.BusinessException;
import gift.global.response.ErrorCode;

public class ProductValidException extends BusinessException {
    public ProductValidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
