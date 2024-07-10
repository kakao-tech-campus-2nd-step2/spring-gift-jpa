package gift.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // Authentication Error
    AUTHENTICATION_INVALID(HttpStatus.UNAUTHORIZED, "인증이 유효하지 않습니다."),
    AUTHENTICATION_FAILED(HttpStatus.FORBIDDEN, "인증에 실패하였습니다. 로그인을 다시 시도해주세요."),
    AUTHENTICATION_EXPIRED(HttpStatus.FORBIDDEN, "인증이 만료되었습니다."),

    // Product Error
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 상품은 존재하지 않습니다."),

    // Member Error
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원 계정입니다."),
    MEMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 회원 계정입니다."),

    // Wish Error
    WISH_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 상품을 위시리스트에서 찾을 수 없습니다."),
    WISH_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 해당 상품이 위시리스트에 존재합니다.")
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
