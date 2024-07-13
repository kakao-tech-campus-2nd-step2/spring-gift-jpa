package gift.domain.wishlist.dto;

public class ProductIdRequest {

    private Long productId;

    public ProductIdRequest() {
    }

    public ProductIdRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
