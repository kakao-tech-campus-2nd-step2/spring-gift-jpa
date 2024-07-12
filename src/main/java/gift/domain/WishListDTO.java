package gift.domain;

public class WishListDTO {
    private Long userId;
    private Long productId;

    public WishListDTO(){}

    public WishListDTO(Long userId, Long productId) {
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

