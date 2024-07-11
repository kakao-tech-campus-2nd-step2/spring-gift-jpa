package gift.global.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedMemberException extends GlobalException {

    private static final String MESSAGE = "Unauthorized member.";
    public static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;

    public UnauthorizedMemberException() {
        super(MESSAGE, STATUS);
    }
}
