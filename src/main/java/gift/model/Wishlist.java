package gift.model;

public class Wishlist {
    private long id;
    private long userId;
    private long productId;

    public Wishlist(long id, long userId, long productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }

    public Wishlist(){}

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
}
