package gift.dto;

import gift.entity.WishEntity;

public class WishRequest {

    private Long productId;

    private Long memberId;

    public WishRequest() {
    }

    public WishRequest(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public WishEntity toWishEntity() {
        return new WishEntity(this.memberId, this.productId);
    }
}
