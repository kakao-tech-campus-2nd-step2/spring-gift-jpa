package gift.dto.request;

public class WishListRequest {

    Long memberId;
    Long productId;

    public WishListRequest(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

}
