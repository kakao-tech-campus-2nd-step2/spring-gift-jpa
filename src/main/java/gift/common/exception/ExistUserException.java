package gift.common.exception;

public class ExistUserException extends RuntimeException{

    public ExistUserException() {
        super("이미 가입된 회원입니다.");
    }
}
