package gift.exception;

import gift.entity.Wish;

public class WishAlreadyExistsException extends RuntimeException {

    public WishAlreadyExistsException(Wish wish) {
        super("ProductId: " + wish.getProduct().getId() + " already exist in your wishlist");
    }

}
