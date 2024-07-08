package gift.dto;

import gift.exception.ProductErrorCode;

public class ProductErrorResponse {

    private ProductErrorCode errorCode;
    private String errorMessage;

    public ProductErrorResponse(ProductErrorCode errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ProductErrorCode getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorCode(ProductErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
