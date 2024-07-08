package gift.product.model;

public class WishProduct2 {
    private String name;
    private int price;
    private String imageUrl;
    private int count;

    public WishProduct2(String name, int price, String imageUrl, int count) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.count = count;
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

    public int getCount() {
        return count;
    }
}
