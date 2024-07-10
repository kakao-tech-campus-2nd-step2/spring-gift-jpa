package gift.entity;

import gift.compositeKey.WishListId;
import jakarta.persistence.*;

@Entity
public class WishList {

    @EmbeddedId
    private WishListId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    // getters and setters

    public WishList(WishListId id) {
        this.id = id;
    }

    public WishList() {
    }

    public void setId(WishListId id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }

    public WishListId getId() {
        return id;
    }
}
