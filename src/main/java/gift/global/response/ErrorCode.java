package gift.global.response;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // Product
    PRODUCT_NOT_FOUND_ERROR(HttpStatus.BAD_REQUEST, "EP001", "Product Not Found Error"),
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
