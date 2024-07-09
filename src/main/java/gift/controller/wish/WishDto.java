package gift.controller.wish;

import gift.domain.Wish;

public record WishDto(Long productId, Long count) {
    public static WishDto of(Wish wish) {
        return new WishDto(wish.productId(), wish.count());
    }
}
