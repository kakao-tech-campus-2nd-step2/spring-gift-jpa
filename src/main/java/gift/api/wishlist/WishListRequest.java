package gift.api.wishlist;

public record WishListRequest(
    Long productId,
    Integer quantity
) {}
