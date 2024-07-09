package gift.entity;

public class Wish {
    private long id;
    private long productId;
    private long userId;

    public Wish(long id, long productId, long userId) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
    }

    public long getId() { return id; }
    public long getProductId() { return productId; }
    public long getUserId() { return userId; }
}
