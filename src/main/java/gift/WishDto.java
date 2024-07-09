package gift;

public class WishDto {
    private Long memberId;
    private Long productId;
    private String productName;

    public WishDto(Long memberId, Long productId , String productName) {
        this.memberId = memberId;
        this.productId = productId;
        this.productName = productName;
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

    public String getProductName() {
        return productName;
    }
}
