package gift.Entity;

import gift.Model.MemberDto;
import gift.Model.ProductDto;
import gift.Model.WishlistDto;
import jakarta.persistence.*;

@Entity
public class Wishlist {

    @EmbeddedId
    private WishlistId id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @MapsId("userId")
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @MapsId("productId")
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Product product;

    private String productName;
    private int count;
    private int price;

    protected Wishlist() {
    }

    public Wishlist(WishlistId id, Member member, Product product, String productName, int count, int price) {
        this.id = id;
        this.member = member;
        this.product = product;
        this.productName = productName;
        this.count = count;
        this.price = price;
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