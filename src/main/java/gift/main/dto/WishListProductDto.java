package gift.main.dto;

public class WishListProductDto {
    public WishListProductDto(Long productId, Long userId) {
        this.productId = productId;
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getUserId() {
        return userId;
    }

    private final Long productId;
    private final Long userId;
}
