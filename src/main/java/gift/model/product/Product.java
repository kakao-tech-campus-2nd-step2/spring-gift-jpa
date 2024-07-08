package gift.model.product;

import gift.validate.ProductNameValidator;

public class Product {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;

    public Product(Long id, String name, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static Product create(Long id, String name, Integer price, String imageUrl) {
        ProductNameValidator.isValid(name);
        return new Product(id, name, price, imageUrl);
    }

}
