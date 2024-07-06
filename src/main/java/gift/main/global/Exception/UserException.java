package gift.main.global.Exception;

import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException {
    private final HttpStatus httpStatus;

    public UserException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
