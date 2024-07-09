package gift.dto;

import gift.model.Product;

public class ProductResponseDTO {
    private String name;
    private int price;
    private String imageUrl;

    public ProductResponseDTO(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
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
