package gift.model;

public class WishList {
    private Long userId;
    private Long productId;

    public WishList(){}

    public WishList(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;

    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

}

