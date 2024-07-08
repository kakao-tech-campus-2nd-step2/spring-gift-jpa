package gift.exception;

public class MemberNotFoundException extends RuntimeException{

    private static final String MESSAGE = "아이디 또는 비밀번호가 일치하지 않습니다.";

    public MemberNotFoundException(){
        super(MESSAGE);
    }

    public MemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
