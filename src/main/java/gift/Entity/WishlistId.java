package gift.Entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class WishlistId implements Serializable {

    private long userId;
    private long productId;

    protected WishlistId() {}

    public WishlistId(long userId, long productId) {
        this.userId = userId;
        this.productId = productId;
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