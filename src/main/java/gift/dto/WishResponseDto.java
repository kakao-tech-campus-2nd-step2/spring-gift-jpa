package gift.dto;

import gift.domain.wish.Wish;

public class WishResponseDto {
    private final Long wishId;
    private final Long userId;
    private final Long productId;
    private final Integer quantity;

    public WishResponseDto(Wish wish) {
        this.wishId = wish.getId();
        this.userId = wish.getMemberId();
        this.productId = wish.getProductId();
        this.quantity = wish.getQuantity();
    }

    public Long getWishId() {
        return wishId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
