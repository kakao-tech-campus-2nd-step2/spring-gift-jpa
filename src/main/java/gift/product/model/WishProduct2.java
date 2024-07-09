package gift.product.model;

public class WishProduct2 {
    private String name;
    private int price;
    private String imageUrl;

    public WishProduct2(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
