package gift.wishlist;

public class WishListDTO {

    private String email;
    private long productId;
    private int num;

    public WishListDTO() {}

    public WishListDTO(String email, long productId, int num) {
        this.email=email;
        this.productId = productId;
        this.num = num;
    }

    public String getEmail() {
        return email;
    }

    public long getProductId() {
        return productId;
    }

    public int getNum() {
        return num;
    }

    public WishList toWishList() {
        return new WishList(email, productId, num);
    }

    public static WishListDTO fromWishList(WishList wishList) {
        return new WishListDTO(wishList.getEmail(), wishList.getProductId(), wishList.getNum());
    }
}
