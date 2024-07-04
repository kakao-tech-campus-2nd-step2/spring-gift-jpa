package gift.main.error;

public class ProductError extends IllegalArgumentException {
    public ProductError(String message) {
        super(message);
    }
}
