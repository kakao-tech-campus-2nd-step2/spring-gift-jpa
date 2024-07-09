package gift.main.entity;

public class WishlistProduct {
    private final Long id;
    private final Long productId;
    private final Long userId;

    public WishlistProduct(Long id, Long productId, Long userId) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getUserId() {
        return userId;
    }
}
