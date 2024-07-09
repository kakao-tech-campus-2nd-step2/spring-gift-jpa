package gift;

public class Wish {
    private Long memberId;
    private Long productId;
    private String productName;

    public Wish(Long memberId, Long productId, String productName) {
        this.memberId = memberId;
        this.productId = productId;
        this.productName = productName;
    }

    public Wish(Long memberId) {
        this.memberId = memberId;
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
