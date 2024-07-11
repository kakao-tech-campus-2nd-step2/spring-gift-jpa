package gift.wishlist.entity;

public class WishList {

    private long userId;
    private long productId;
    private int quantity;

    public WishList(long userId, long productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getUserId() {
        return userId;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
