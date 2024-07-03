package gift.util;

public enum ErrorCode {
    // Product ErrorMessage
    INVALID_NAME("상품의 이름은 필수입니다.", 400),
    NAME_TOO_LONG("상품의 이름은 최대 15자입니다.", 400),
    INVALID_PRICE("상품의 가격은 필수입니다.", 400),
    NEGATIVE_PRICE("상품의 가격은 0보다 커야합니다.", 400),
    ;

    private final String message;
    private final int status;

    ErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
