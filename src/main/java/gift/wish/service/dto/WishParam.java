package gift.wish.service.dto;

public record WishParam(
        Long productId,
        Long userId,
        Integer amount
) {
}
