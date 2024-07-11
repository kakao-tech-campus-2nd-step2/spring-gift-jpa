package gift.api.wishlist;

import gift.global.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class InvalidQuantityException extends GlobalException {

    private static final String MESSAGE = "Quantity must be more than 0.";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public InvalidQuantityException() {
        super(MESSAGE, STATUS);
    }
}
