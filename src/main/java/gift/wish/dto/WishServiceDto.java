package gift.wish.dto;

import gift.wish.domain.ProductCount;
import gift.wish.domain.Wish;

public record WishServiceDto(Long id, Long memberId, Long productId, ProductCount productCount) {
    public Wish toWish() {
        return new Wish(id, memberId, productId, productCount);
    }
}
