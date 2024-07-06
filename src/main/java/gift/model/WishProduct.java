package gift.model;

public class WishProduct {
    private Long memberId;
    private Long productId;

    public WishProduct(Long memberId, Long productId) {
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
