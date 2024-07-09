package gift.exception;

public class PasswordNotMatchedException extends RuntimeException{
    public PasswordNotMatchedException() {
        super("Password is wrong");
    }
}
