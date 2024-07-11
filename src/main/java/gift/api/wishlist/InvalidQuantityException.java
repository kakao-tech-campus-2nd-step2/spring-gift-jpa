package gift.api.wishlist;

public class InvalidQuantityException extends IllegalArgumentException {

    private static final String MESSAGE = "Quantity must be more than 0.";

    public InvalidQuantityException() {
        super(MESSAGE);
    }
}
