package gift.dto.request;

public record WishListRequest(
        Long productId,

        int amount
) {
}
