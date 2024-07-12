package gift.DTO;

public class WishRequest {

    private Long productId;

    WishRequest(Long productId) {
        this.productId = productId;
    }

    WishRequest() {}

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}