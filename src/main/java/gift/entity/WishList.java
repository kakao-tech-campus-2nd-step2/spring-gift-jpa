package gift.entity;

import gift.entity.compositeKey.WishListId;
import jakarta.persistence.*;

@Entity
public class WishList {

    @EmbeddedId
    private WishListId id;


    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
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
