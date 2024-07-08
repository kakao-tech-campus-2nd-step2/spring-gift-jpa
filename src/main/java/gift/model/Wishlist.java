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

    public Wishlist(long userId, long productId) {
        this.id = 0;
        this.userId = userId;
        this.productId = productId;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getProductId() {
        return productId;
    }
}
