package gift.controller.wish;

import gift.domain.Wish;

public record WishRequest(Long productId, Long count) {
    public static WishRequest of(Wish wish) {
        return new WishRequest(wish.getProductId(), wish.getCount());
    }
}
