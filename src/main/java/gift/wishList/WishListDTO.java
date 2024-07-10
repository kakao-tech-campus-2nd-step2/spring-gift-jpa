package gift.wishList;

public class WishListDTO {
    long productID;

    long count;

    public WishListDTO() {
    }

    public WishListDTO(long productID, long count) {
        this.productID = productID;
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

}
