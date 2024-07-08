package gift.wishList;

public class WishList {
    long id;
    long userID;
    long productID;
    long count;

    public WishList(long id, long userID, long productID, long count) {
        this.id = id;
        this.userID = userID;
        this.productID = productID;
        this.count = count;
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
