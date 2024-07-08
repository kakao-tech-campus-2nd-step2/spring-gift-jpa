package gift.dto.request;

public class WishListAddRequest {

    Long productId;
    int amount;

    public WishListAddRequest(Long productId, int amount) {
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
