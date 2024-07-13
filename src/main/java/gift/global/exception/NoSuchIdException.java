package gift.global.exception;

import org.springframework.http.HttpStatus;

public class NoSuchIdException extends GlobalException {

    private static final String MESSAGE_FORMAT = "No such %s exists.";

    public NoSuchIdException(String element) {
        super(String.format(MESSAGE_FORMAT, element), HttpStatus.BAD_REQUEST);
    }
}
