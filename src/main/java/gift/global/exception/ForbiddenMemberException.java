package gift.global.exception;

public class ForbiddenMemberException extends RuntimeException {

    private static final String MESSAGE = "Forbidden member.";

    public ForbiddenMemberException() {
        super(MESSAGE);
    }
}
