package gift.controller.dto.response;

public record WishResponse(
        Long id,
        Long productId,
        int amount
) {
    public static WishResponse fromModel(gift.model.Wish wish) {
        return new WishResponse(wish.getId(), wish.getProductId(), wish.getAmount());
    }
}
