package gift.product.entity;

// 상품의 정보를 담는 엔터티
public class Product {

    private long productId;
    private String name;
    private int price;
    private String image;

    public Product(long productId, String name, int price, String image) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
