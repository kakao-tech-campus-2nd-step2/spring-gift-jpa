package gift.model.dto;

import gift.model.Product;

public class ProductResponseDto {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;

    public ProductResponseDto() {
    }

    public ProductResponseDto(Long id, String name, int price, String imageUrl) {
        this.imageUrl = imageUrl;
        this.price = price;
        this.name = name;
        this.id = id;
    }

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

    public static ProductResponseDto from(Product product) {
        return new ProductResponseDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl()
        );
    }
}
