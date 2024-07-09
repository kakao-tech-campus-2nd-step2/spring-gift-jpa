package gift.exception;

public class MemberAlreadyExistsException extends RuntimeException {
    public MemberAlreadyExistsException() {
        super("Member already exists");
    }
}
