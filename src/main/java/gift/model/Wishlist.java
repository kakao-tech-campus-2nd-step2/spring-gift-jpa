package gift.model;

public class Wishlist {
    private String userId;
    private Long productId;

    public Wishlist(String userId, Long productId) {
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