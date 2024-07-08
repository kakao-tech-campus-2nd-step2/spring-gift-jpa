package gift.exception;

public class MemberAuthenticationException extends RuntimeException{
    public MemberAuthenticationException(String message){
        super(message);
    }
}
