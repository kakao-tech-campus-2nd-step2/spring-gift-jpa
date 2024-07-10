package gift.doamin.wishlist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidWishListFormException extends ResponseStatusException {

    public InvalidWishListFormException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
