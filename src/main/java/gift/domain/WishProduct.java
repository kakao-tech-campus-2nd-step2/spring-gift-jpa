package gift.domain;

public class WishProduct {
    String userId;
    Long productId;

    public WishProduct(String userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }
}
