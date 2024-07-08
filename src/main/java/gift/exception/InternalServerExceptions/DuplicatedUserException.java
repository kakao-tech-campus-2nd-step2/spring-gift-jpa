package gift.exception.InternalServerExceptions;

public class DuplicatedUserException extends InternalServerException {
    public DuplicatedUserException(){ super(); }
    public DuplicatedUserException(String message){ super(message); }
}
