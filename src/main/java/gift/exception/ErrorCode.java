package gift.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INVALID_PRODUCT(HttpStatus.BAD_REQUEST, "유효하지 않은 상품정보입니다."),
    INVALID_MEMBER(HttpStatus.BAD_REQUEST, "유효하지 않은 멤버정보입니다."),
    INVALID_WISH(HttpStatus.BAD_REQUEST, "유효하지 않은 위시정보입니다.");
    private final HttpStatus error;

    private final String message;

    ErrorCode(HttpStatus error, String message) {
        this.error = error;
        this.message = message;
    }

    public HttpStatus getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
