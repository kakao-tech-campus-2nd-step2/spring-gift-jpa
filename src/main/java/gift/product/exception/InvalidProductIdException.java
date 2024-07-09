package gift.product.exception;

public class InvalidProductIdException extends RuntimeException{
    public InvalidProductIdException(String message) {
        super(message);
    }
}
