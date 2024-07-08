package gift.exception;

public class WishListException extends RuntimeException {

    public WishListException() {
        super();
    }

    public WishListException(String message) {
        super(message);
    }

    public WishListException(String message, Throwable cause) {
        super(message, cause);
    }

    public WishListException(Throwable cause) {
        super(cause);
    }

    protected WishListException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
