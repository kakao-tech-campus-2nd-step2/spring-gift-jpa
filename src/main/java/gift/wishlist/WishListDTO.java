package gift.wishlist;

public class WishListDTO {

    private String email;
    private String name;
    private int num;

    public WishListDTO() {}

    public WishListDTO(String email, String name, int num) {
        this.email=email;
        this.name = name;
        this.num = num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public WishList toWishList() {
        return new WishList(email, name, num);
    }

    public static WishListDTO fromWishList(WishList wishList) {
        return new WishListDTO(wishList.getEmail(), wishList.getName(), wishList.getNum());
    }
}
