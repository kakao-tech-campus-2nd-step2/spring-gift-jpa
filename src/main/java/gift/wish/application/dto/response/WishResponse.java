package gift.wish.application.dto.response;

import gift.wish.domain.Wish;

public record WishResponse(
        Long id,
        Long productId,
        int amount
) {
    public static WishResponse fromModel(Wish wish) {
        return new WishResponse(wish.getId(), wish.getProductId(), wish.getAmount());
    }
}
