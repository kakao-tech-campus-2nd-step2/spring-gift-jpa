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
    private WishListId wishId;

    @Column(nullable = false)
    private int quantity;

    public WishList() {
    }
}
