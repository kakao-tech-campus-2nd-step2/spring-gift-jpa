package gift.main.Exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    //회원가입시
    EMPTY_EMAIL(HttpStatus.BAD_REQUEST, "이메일을 채워주세요"),
    EMPTY_NAME(HttpStatus.BAD_REQUEST, "이름을 채워주세요"),
    EMPTY_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 채워주세요"),

    NO_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 비어있습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    ALREADY_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    ERROR_LOGIN(HttpStatus.BAD_REQUEST, "이메일과 비밀번호를 정확히 입력해주세요"),
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
