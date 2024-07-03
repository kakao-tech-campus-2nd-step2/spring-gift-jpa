package gift.product.exception;

import gift.util.ErrorCode;

public class ProductException extends RuntimeException {

    private final ErrorCode errorCode;

    public ProductException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

