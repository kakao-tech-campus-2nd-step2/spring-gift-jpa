package gift.global.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenMemberException extends GlobalException {

    private static final String MESSAGE = "Forbidden member.";
    private static final HttpStatus STATUS = HttpStatus.FORBIDDEN;

    public ForbiddenMemberException() {
        super(MESSAGE, STATUS);
    }
}
