package gift.domain.exception;

public class TokenUnexpectedErrorException extends RuntimeException {

    public TokenUnexpectedErrorException() {
        super("알 수 없는 오류가 발생했습니다. 토큰 재발급이 필요합니다.");
    }
}
