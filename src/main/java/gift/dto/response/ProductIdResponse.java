package gift.dto.response;

public class ProductIdResponse {

    private final Long productId;

    public ProductIdResponse(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

}
