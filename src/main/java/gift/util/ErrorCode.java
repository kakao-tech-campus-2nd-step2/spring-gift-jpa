package gift.util;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // Product ErrorMessage
    INVALID_NAME("상품의 이름은 필수입니다.", HttpStatus.BAD_REQUEST),
    NAME_HAS_RESTRICTED_WORD("상품의 이름에 금지어가 포함되어 있습니다.", HttpStatus.BAD_REQUEST),
    NAME_TOO_LONG("상품의 이름은 최대 15자입니다.", HttpStatus.BAD_REQUEST),
    INVALID_PRICE("상품의 가격은 필수입니다.", HttpStatus.BAD_REQUEST),
    NEGATIVE_PRICE("상품의 가격은 0보다 커야합니다.", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND("해당 상품을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),

    // User ErrorMessage
    DUPLICATE_USER("이미 존재하는 회원입니다.", HttpStatus.BAD_REQUEST),

    // User ErrorMessage
    LOGIN_FAILED("로그인에 실패하였습니다.", HttpStatus.UNAUTHORIZED),

    USER_NOT_FOUND("유저를 찾을 수 없습니다.", HttpStatus.UNAUTHORIZED),


    // Wishlist ErrorMessage
    WISHLIST_NOT_FOUND("해당 위시리스트를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
