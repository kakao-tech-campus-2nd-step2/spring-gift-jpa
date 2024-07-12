package gift.dto;

import gift.entity.WishEntity;

public class WishRequest {

    private Long memberId;

    private Long productId;

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
}
