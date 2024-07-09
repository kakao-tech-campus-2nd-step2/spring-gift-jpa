package gift.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException() {
        super("Product already exists");
    }
}
