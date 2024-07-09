package gift.vo;

import gift.validation.ValidName;
import jakarta.validation.constraints.Size;

public class Product {

    private Long id;

    @Size(max=15) @ValidName
    private String name;

    private int price;

    private String imageUrl;

    Product() {}

    public Product(String name, int price, String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public Product(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // getter
    public Long getId() {
        return id;
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
