package gift.global.exception;

public class UnauthorizedMemberException extends RuntimeException {

    private static final String MESSAGE = "Unauthorized member.";

    public UnauthorizedMemberException() {
        super(MESSAGE);
    }
}
