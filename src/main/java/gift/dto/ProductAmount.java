package gift.dto;

public class ProductAmount {

    private Long productId;
    private int amount;

    public ProductAmount(Long productId, int amount) {
        this.productId = productId;
        this.amount = amount;
    }

    // getters and setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
