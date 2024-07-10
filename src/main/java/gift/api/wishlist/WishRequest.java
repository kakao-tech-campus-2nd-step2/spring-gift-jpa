package gift.api.wishlist;

public record WishRequest(
    Long productId,
    Integer quantity
) {}
