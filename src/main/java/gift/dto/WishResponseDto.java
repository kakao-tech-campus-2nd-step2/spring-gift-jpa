package gift.dto;

import gift.domain.wish.Wish;

public class WishResponseDto {
    private final Long wishId;
    private final Long memberId;
    private final Long productId;
    private final String productName;

    private final Integer quantity;

    public WishResponseDto(Wish wish) {
        this.wishId = wish.getId();
        this.memberId = wish.getMember().getId();
        this.productId = wish.getProduct().getId();
        this.productName = wish.getProduct().getName();
        this.quantity = wish.getQuantity();
    }

    public Long getWishId() {
        return wishId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
