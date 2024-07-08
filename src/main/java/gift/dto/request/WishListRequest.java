package gift.dto.request;

public class WishListRequest {

    Long productId;
    int amount;

    public WishListRequest(Long productId, int amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public Long getProductId() {
        return productId;
    }

    public int getAmount() {
        return amount;
    }

}
