package gift.domain.exception;

public class ProductNotIncludedInWishlistException extends RuntimeException {

    public ProductNotIncludedInWishlistException() {
        super("The product is not included your wishlist.");
    }
}
