package gift.main.Exception;


import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;

    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.httpStatus = errorCode.getHttpStatus();

    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
