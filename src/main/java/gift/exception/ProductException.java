package gift.exception;

public class ProductException extends RuntimeException {

    public ProductException() {
        super();
    }

    public ProductException(String message) {
        super(message);
    }

    public ProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductException(Throwable cause) {
        super(cause);
    }

    protected ProductException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
