package gift.exception;

public class InvalidRequestException extends RuntimeException{
    private final ErrorCode errorCode;

    public InvalidRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
