package gift.wish.service.dto;

import gift.wish.domain.Wish;

public record WishParam(
        Long productId,
        Long userId,
        Integer amount
) {
    public Wish toEntity() {
        return new Wish(userId, productId, amount);
    }
}
