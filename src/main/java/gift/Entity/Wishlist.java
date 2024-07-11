package gift.Entity;

import gift.Model.WishListItem;
import jakarta.persistence.*;

@Entity
public class Wishlist {

    @EmbeddedId
    private WishlistId id;
    private String productName;
    private int count;
    private int price;

    protected Wishlist() {
    }

    protected Wishlist(WishlistId id, String productName, int count, int price) {
        this.id = id;
        this.productName = productName;
        this.count = count;
        this.price = price;
    }

    public static Wishlist createWishlist(WishListItem wishListItem) {
        return new Wishlist(WishlistId.createWishlistId(wishListItem.getUserId(), wishListItem.getProductId()), wishListItem.getProductName(), wishListItem.getCount(), wishListItem.getPrice());
    }

    public long getUserId() {
        return id.getUserId();
    }

    public void setUserId(long userId) {
        id.setUserId(userId);
    }

    public long getProductId() {
        return id.getProductId();
    }

    public void setProductId(long productId) {
        id.setProductId(productId);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}