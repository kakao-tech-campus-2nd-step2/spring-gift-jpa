package gift.Model;

public class RequestWishListDTO {
    private Long productId;
    private int count;

    public RequestWishListDTO() {
    }

    public RequestWishListDTO(Long productId, int count) {
        this.productId = productId;
        this.count = count;
    }

    public Long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }
}
