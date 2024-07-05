package gift.main.global.Exception;

public class TokenException extends RuntimeException{
    public TokenException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
