package gift.dto;

import gift.entity.Product;

public class ProductDTO {

    private String name;
    private int price;
    private String imageUrl;

    public ProductDTO() {
    }


    public ProductDTO(Long id, String name, int price, String imageUrl) {
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

    public Product toEntity() {
        return new Product(name, price, imageUrl);
    }


}
