package gift.DTO;

public class WishProductDTO {
    String userId;
    Long productId;
    int count;

    public WishProductDTO(String userId, Long productId, int count) {
        this.userId = userId;
        this.productId = productId;
        this.count = count;
    }

    public String getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }
    public int getCount(){
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
