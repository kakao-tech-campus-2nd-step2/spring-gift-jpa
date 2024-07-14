package gift.wishlist;

public class WishListDTO {

    private long userId;
    private long productId;
    private int num;

    public WishListDTO() {
    }

    public WishListDTO(long userId, long productId, int num) {
        this.userId = userId;
        this.productId = productId;
        this.num = num;
    }

    public long getProductId() {
        return productId;
    }

    public int getNum() {
        return num;
    }

    public static WishListDTO fromWishList(WishList wishList) {
        return new WishListDTO(wishList.getUser().getId(), wishList.getProduct().getId(),
            wishList.getNum());
    }
}
