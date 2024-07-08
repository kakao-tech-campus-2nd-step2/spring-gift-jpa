package gift.common.validation;

public class ValidateErrorMessage {
    public static final String INVALID_PRODUCT_NAME_NULL = "상품명은 필수 입력값입니다.";
    public static final String INVALID_PRODUCT_NAME_PATTERN = "상품이름에는 한글, 영어 대소문자, 숫자, ( ), [ ], +, -, &, /, _만 포함 가능합니다.";
    public static final String INVALID_PRODUCT_NAME_LENGTH = "상품명은 최대 15자까지 입력 가능합니다.";
    public static final String INVALID_PRODUCT_PRICE_NULL = "가격은 필수 입력값입니다.";
    public static final String INVALID_PRODUCT_PRICE_RANGE = "가격은 1원 이상, 21억원 이하이어야 합니다.";
    public static final String INVALID_PRODUCT_IMG_URL_NULL = "이미지 URL은 필수 입력값입니다.";
    public static final String INVALID_PRODUCT_IMG_URL_FORMAT = "이미지 URL 형식이 올바르지 않습니다.";

    public static final String INVALID_USER_NAME_NULL = "사용자 이름은 필수 입력값입니다.";
    public static final String INVALID_USER_NAME_PATTERN = "사용자 이름은 이메일 형식이어야 합니다.";
    public static final String INVALID_USER_PASSWORD_NULL = "비밀번호는 필수 입력값입니다.";
}
