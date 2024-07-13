package gift.wishlist.entity;

import gift.wishlist.model.WishListId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "wish_products")
public class WishList {

    @EmbeddedId
    private WishListId wishListId;

    @Column(nullable = false)
    private int quantity;

    public WishList() {
    }

    public WishList(WishListId wishListId, int quantity) {
        this.wishListId = wishListId;
        this.quantity = quantity;
    }

    public WishListId getWishListId() {
        return wishListId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }
}
