package gift.web.validation.exception.code;

/**
 * 에러 코드<br>
 * code - HTTP 상태 코드 + 두 자리 숫자(내부 규칙 정의) 으로 정의된다.<br>
 * description - 에러 코드에 대한 설명
 */
public enum ErrorCode {

    UNAUTHORIZED_INVALID_CREDENTIALS(-40100, Category.AUTHENTICATION, "유효하지 않은 신원 정보입니다."),
    UNAUTHORIZED_INVALID_TOKEN(-40101, Category.AUTHENTICATION, "유효하지 않은 토큰 입니다."),
    UNAUTHORIZED_INVALID_TOKEN_EXPIRATION(-40102, Category.AUTHENTICATION, "토큰이 만료되었습니다."),

    INVALID_REQUEST(-40000, Category.COMMON, "잘못된 요청입니다."),
    INVALID_PARAMETER(-40001, Category.COMMON, "잘못된 값 전달입니다."),
    KAKAO_APPROVAL_NEEDED(-40002, Category.POLICY, "담당 MD와 협의한 경우에만 사용가능한 키워드가 존재합니다."),
    SPECIAL_CHARACTER_NOT_ALLOWED(-40003, Category.POLICY, "'(', ')', '[', ']', '+', '-', '&', '/', '_' 외의 특수문자는 사용할 수 없습니다."),
    INVALID_PASSWORD_FORMAT(-40004, Category.POLICY, "비밀번호는 영문 대소문자, 숫자를 포함하여 8자 이상 15자 이하로 입력해주세요."),
    INCORRECT_PASSWORD(-40005, Category.AUTHENTICATION, "비밀번호가 일치하지 않습니다."),
    INCORRECT_EMAIL(-40006, Category.AUTHENTICATION, "이메일 정보와 일치하는 회원이 존재하지 않습니다."),

    NOT_FOUND(-40400, Category.COMMON, "존재하지 않는 요청 URL 입니다."),

    INTERNAL_SERVER_ERROR(-50000, Category.COMMON, "서버 내부 오류입니다."),
    ;

    private final int code;
    private final Category category;
    private final String description;

    ErrorCode(int code, Category category, String description) {
        this.code = code;
        this.category = category;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public Category getCategory() {
        return category;
    }

    public final String getDescription() {
        return description;
    }

    /**
     * 컴파일 타임 상수
     */
    public static class DESCRIPTION {
        public static final String KAKAO_APPROVAL_NEEDED = "담당 MD와 협의한 경우에만 사용가능한 키워드가 존재합니다.";
        public static final String SPECIAL_CHARACTER_NOT_ALLOWED = "'(', ')', '[', ']', '+', '-', '&', '/', '_' 외의 특수문자는 사용할 수 없습니다.";
        public static final String INVALID_PASSWORD_FORMAT = "비밀번호는 영문 대소문자, 숫자를 포함하여 8자 이상 15자 이하로 입력해주세요.";
    }
}
