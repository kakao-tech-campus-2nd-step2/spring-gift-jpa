package gift.exception;

import org.springframework.http.HttpStatus;

import static gift.constant.Message.*;

public enum ErrorCode {

    LENGTH_ERROR(400, HttpStatus.BAD_REQUEST, LENGTH_ERROR_MSG),
    SPECIAL_CHAR_ERROR(400, HttpStatus.BAD_REQUEST, SPECIAL_CHAR_ERROR_MSG),
    KAKAO_CONTAIN_ERROR(400, HttpStatus.BAD_REQUEST, KAKAO_CONTAIN_ERROR_MSG);

    private int status;
    private HttpStatus httpStatus;
    private String message;

    ErrorCode(int status, HttpStatus httpStatus, String message) {
        this.status = status;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
