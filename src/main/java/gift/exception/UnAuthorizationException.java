package gift.exception;

public class UnAuthorizationException extends RuntimeException{

    private static final String MESSAGE = "인증되지 않은 사용자 입니다. 다시 로그인 해주세요.";

    public UnAuthorizationException(){
        super(MESSAGE);
    }

    public UnAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
