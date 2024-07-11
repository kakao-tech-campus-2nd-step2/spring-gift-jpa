package gift.dto;

public class WishlistDTO {
    private Long memberId;
    private Long productId;

    public WishlistDTO() {}

    public WishlistDTO(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
