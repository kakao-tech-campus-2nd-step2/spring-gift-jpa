package gift.main.Exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    //회원가입시
    EMPTY_EMAIL(HttpStatus.BAD_REQUEST, "이메일을 채워주세요"),
    EMPTY_NAME(HttpStatus.BAD_REQUEST, "이름을 채워주세요"),
    EMPTY_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 채워주세요"),

    //인증관련
    NO_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 비어있습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    NO_PERMISSION(HttpStatus.UNAUTHORIZED, "해당 페이지에 권합이 없습니다."),
    NO_PERMISSION_PRODUCT(HttpStatus.UNAUTHORIZED, "해당 상품에 권합이 없습니다."),

    //로그인
    ALREADY_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    ERROR_LOGIN(HttpStatus.BAD_REQUEST, "이메일과 비밀번호를 정확히 입력해주세요"),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "이메일과 비밀번호를 정확히 입력해주세요"),

    //제품관련
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 제품을 찾을 수 없습니다."),
    ALREADY_EXISTING_WISH_LIST(HttpStatus.BAD_REQUEST, "해당 제품은 이미 담겨있습니다."),
    ;
    //이메일과 비밀번호 코드가 401이 아닌 400인 이유: 코드를 보고 해당 유저가 있다고 판단할 것 같아서


    private final HttpStatus httpStatus;
    private final String errorMessage;


    ErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
