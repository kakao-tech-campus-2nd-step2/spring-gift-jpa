package gift.exception;

public class ForbiddenException extends RuntimeException{

    private static final String MESSAGE = "접근할 수 있는 권한을 가지고 있지 않습니다.";

    public ForbiddenException(){
        super(MESSAGE);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
