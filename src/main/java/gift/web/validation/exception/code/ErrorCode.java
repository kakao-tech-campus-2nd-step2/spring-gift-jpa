package gift.web.validation.exception.code;

/**
 * 에러 코드<br>
 * code - HTTP 상태 코드 + 두 자리 숫자(내부 규칙 정의) 으로 정의된다.<br>
 */
public enum ErrorCode {

    UNAUTHORIZED_INVALID_CREDENTIALS(-40100, Category.AUTHENTICATION),
    UNAUTHORIZED_INVALID_TOKEN(-40101, Category.AUTHENTICATION),

    BAD_REQUEST(-40000, Category.COMMON),
    INVALID_PARAMETER(-40001, Category.COMMON),
    KAKAO_APPROVAL_NEEDED(-40002, Category.POLICY),
    SPECIAL_CHARACTER_NOT_ALLOWED(-40003, Category.POLICY),
    INVALID_PASSWORD_FORMAT(-40004, Category.POLICY),
    INCORRECT_PASSWORD(-40005, Category.AUTHENTICATION),
    INCORRECT_EMAIL(-40006, Category.AUTHENTICATION),

    NOT_FOUND(-40400, Category.COMMON),

    INTERNAL_SERVER_ERROR(-50000, Category.COMMON),
    ;

    private final int code;
    private final Category category;

    ErrorCode(int code, Category category) {
        this.code = code;
        this.category = category;
    }

    public int getCode() {
        return code;
    }

    public Category getCategory() {
        return category;
    }

}
