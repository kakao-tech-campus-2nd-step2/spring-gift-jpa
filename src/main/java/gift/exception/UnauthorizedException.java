package gift.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("Unauthorized");
    }
}
