package gift.api.member;

import gift.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends GlobalException {

    private static final String MESSAGE = "Email already exists.";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public EmailAlreadyExistsException() {
        super(MESSAGE, STATUS);
    }
}
