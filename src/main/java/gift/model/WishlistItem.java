package gift.model;

public class WishlistItem {
    private long id;
    private long userId;
    private long productId;
    private String productName;
    private long amount;

    public WishlistItem(long id, long userId, long productId, String productName, long amount) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
