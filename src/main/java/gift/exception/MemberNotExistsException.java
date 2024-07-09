package gift.exception;

public class MemberNotExistsException extends RuntimeException {
    public MemberNotExistsException() {
        super("Member does not Exist");
    }
}
