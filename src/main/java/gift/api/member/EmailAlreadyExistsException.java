package gift.api.member;

import gift.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends GlobalException {

    private static final String MESSAGE = "Email already exists.";

    public EmailAlreadyExistsException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
