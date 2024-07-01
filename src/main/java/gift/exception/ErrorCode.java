package gift.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 Id에 대한 상품이 존재하지 않습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "요청 값이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상하지 못한 예외가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
