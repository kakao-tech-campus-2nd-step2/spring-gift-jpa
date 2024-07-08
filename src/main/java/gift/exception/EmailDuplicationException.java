package gift.exception;

public class EmailDuplicationException extends RuntimeException{

    private static final String MESSAGE = "중복된 Email 입니다.";

    public EmailDuplicationException(){
        super(MESSAGE);
    }


    public EmailDuplicationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
