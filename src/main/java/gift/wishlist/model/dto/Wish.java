package gift.wishlist.model.dto;

public record Wish(
        Long id,
        Long userId,
        Long productId,
        int quantity,
        boolean status
) {
}