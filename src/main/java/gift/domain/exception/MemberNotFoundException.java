package gift.domain.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super("User not found.");
    }
}
