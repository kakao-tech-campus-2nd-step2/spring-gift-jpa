package gift.wishList;

import jakarta.persistence.*;

@Entity
@Table(name = "WISHLISTS")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name = "userID")
    long userID;
    @Column(name = "productID")
    long productID;
    @Column(name = "count")
    long count;

    public WishList(long userID, long productID, long count) {
        this.userID = userID;
        this.productID = productID;
        this.count = count;
    }

    public WishList() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
