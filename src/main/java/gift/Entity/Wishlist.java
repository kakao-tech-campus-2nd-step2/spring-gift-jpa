package gift.Entity;

import gift.Model.WishListItem;
import jakarta.persistence.*;

@Entity
public class Wishlist {

    @EmbeddedId
    private WishlistId id;
  
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("userId")
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("productId")
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Products products;
    private String productName;
    private int count;
    private int price;

    protected Wishlist() {
    }

    protected Wishlist(WishlistId id, Users users, Products products, String productName, int count, int price) {
        this.id = id;
        this.users = users;
        this.products = products;
        this.productName = productName;
        this.count = count;
        this.price = price;
    }

    public static Wishlist createWishlist(WishListItem wishListItem) {
        Users users = new Users();
        users.setId(wishListItem.getUserId());
        Products products = new Products();
        products.setId(wishListItem.getProductId());

        WishlistId id = new WishlistId(users.getId(), products.getId());
        return new Wishlist(id, users, products, wishListItem.getProductName(), wishListItem.getCount(), wishListItem.getPrice());

    }

    public WishlistId getId() {
        return id;
    }

    public void setId(WishlistId id) {
        this.id = id;
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