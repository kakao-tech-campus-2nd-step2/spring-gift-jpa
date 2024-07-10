package gift.wishlist.dto;

public class WishResponse {
    private Long id;
    private Long productId;

    public WishResponse(Long id, Long productId) {
        this.id = id;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }
}
