package gift.Model;

public class RequestWishDTO {
    private Long productId;
    private int count;

    public RequestWishDTO() {
    }

    public RequestWishDTO(Long productId, int count) {
        this.productId = productId;
        this.count = count;
    }

    public Long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
