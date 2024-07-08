package gift.error;

public class MemberAlreadyExistsException extends RuntimeException {

    public MemberAlreadyExistsException() {
        super("해당 이메일의 사용자 계정이 이미 존재합니다.");
    }

}
