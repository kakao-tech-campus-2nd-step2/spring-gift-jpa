package gift.dto;

public class WishRequest {
    private Long productId;
    private int quantity;

    public WishRequest(){}

    public WishRequest(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // getters, setters

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
