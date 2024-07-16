package gift.dto;

public class RequestWishDto {

    private Long productId;

    public RequestWishDto() {
        this.productId = -1L;
    }

    public RequestWishDto(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
