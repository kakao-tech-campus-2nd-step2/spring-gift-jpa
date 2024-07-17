package gift.dto;

public class WishDto {

    private Long memberId;
    private Long productId;

    public WishDto(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public WishDto(Long productId) {
        this.memberId = null;
        this.productId = productId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }
}
