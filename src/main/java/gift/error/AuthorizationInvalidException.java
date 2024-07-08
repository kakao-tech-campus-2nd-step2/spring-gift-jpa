package gift.error;

public class AuthorizationInvalidException extends RuntimeException {

    public AuthorizationInvalidException() {
        super("인증이 유효하지 않습니다.");
    }

}
