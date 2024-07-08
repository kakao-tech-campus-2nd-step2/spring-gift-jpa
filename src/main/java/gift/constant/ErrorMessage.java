package gift.constant;

public class ErrorMessage {

    /* Validation */
    public static final String VALIDATION_ERROR = "입력 데이터의 유효성을 검사하던 중 문제가 발생했습니다.";

    public static final String PRODUCT_NAME_CONTAINS_KAKAO = "카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.";
    public static final String PRODUCT_NAME_NOT_BLANK = "상품 이름을 입력해 주세요.";
    public static final String PRODUCT_NAME_EXCEEDS_MAX_LENGTH = "상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있습니다.";
    public static final String PRODUCT_NAME_INVALID_CHAR = "상품 이름에 허용되지 않는 특수 문자가 포함되어 있습니다.";
    public static final String PRODUCT_PRICE_NOT_NULL = "상품 가격을 입력해 주세요.";
    public static final String PRODUCT_IMAGE_URL_NOT_NULL = "상품 이미지를 첨부해 주세요.";

    public static final String MEMBER_NAME_NOT_BLANK = "유저 이름을 입력해 주세요.";
    public static final String DUPLICATE_EMAIL = "중복된 이메일 아이디입니다.";
    public static final String EMAIL_NOT_BLANK = "이메일을 입력해 주세요.";
    public static final String PASSWORD_NOT_BLANK = "비밀번호를 입력해 주세요.";
    public static final String MEMBER_NAME_EXCEEDS_MAX_LENGTH = "유저 이름은 공백을 포함하여 최대 15자까지 입력할 수 있습니다.";
    public static final String PASSWORD_LENGTH = "비밀번호는 4자에서 16자까지 입력할 수 있습니다.";
    public static final String INVALID_EMAIL_FORMAT = "올바른 이메일 형식을 입력해 주세요.";

    /* Product */
    public static final String PRODUCT_NOT_FOUND = "존재하지 않는 상품입니다.";

    /* Member */
    public static final String MEMBER_NOT_FOUND = "존재하지 않는 회원입니다.";

    /* JWT */
    public static final String INVALID_TOKEN = "유효하지 않거나 만료된 토큰입니다.";
    public static final String MISSING_TOKEN = "헤더에 토큰이 존재하지 않거나 잘못된 형식입니다.";

    /* MemberService */
    public static final String LOGIN_FAILURE = "이메일 또는 비밀번호가 일치하지 않습니다.";

    /* WishlistService */
    public static final String PRODUCT_ALREADY_IN_WISHLIST = "이미 위시리스트에 추가된 상품입니다.";
    public static final String PRODUCT_NOT_IN_WISHLIST = "이미 위시리스트에 존재하지 않는 상품입니다.";

}
