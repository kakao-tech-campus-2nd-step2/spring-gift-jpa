package gift.domain.exception;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException() {
        super("토큰이 존재하지 않습니다. 발급이 필요합니다.");
    }
}
