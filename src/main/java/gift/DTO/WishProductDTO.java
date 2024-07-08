package gift.DTO;

public class WishProductDTO {
    String userId;
    Long productId;

    public WishProductDTO(String userId, Long productId) {
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
