package gift.product.exception;

public class InvalidProductNameException extends RuntimeException{
    public InvalidProductNameException(String message) {
        super(message);
    }
}
