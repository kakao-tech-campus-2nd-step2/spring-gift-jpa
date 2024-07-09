package gift.constant;

public enum ErrorMessage {

    /* Validation */
    VALIDATION_ERROR("입력 데이터의 유효성을 검사하던 중 문제가 발생했습니다."),

    /* Product */
    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다."),

    /* Member */
    MEMBER_NOT_FOUND("존재하지 않는 회원입니다."),

    /* JWT */
    INVALID_TOKEN("유효하지 않거나 만료된 토큰입니다."),
    MISSING_TOKEN("헤더에 토큰이 존재하지 않거나 잘못된 형식입니다."),

    /* MemberService */
    LOGIN_FAILURE("이메일 또는 비밀번호가 일치하지 않습니다."),

    /* WishlistService */
    PRODUCT_ALREADY_IN_WISHLIST("이미 위시리스트에 추가된 상품입니다."),
    PRODUCT_NOT_IN_WISHLIST("이미 위시리스트에 존재하지 않는 상품입니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
