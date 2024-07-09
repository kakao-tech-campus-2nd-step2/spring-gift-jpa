package gift.domain.exception;

public class TokenStringInvalidException extends RuntimeException {

    public TokenStringInvalidException() {
        super("토큰 문자열이 올바르지 않습니다.");
    }
}
