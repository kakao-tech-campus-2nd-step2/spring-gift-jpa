package gift.DTO;

import gift.domain.Product;

public class ProductResponse {

    private Long id;

    private String name;

    private int price;

    private String imageUrl;

    public ProductResponse() {

    }

    public ProductResponse(Long id, String name, int price, String imageUrl) {
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

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void fromEntity(Product productEntity) {
        this.id = productEntity.getId();
        this.name = productEntity.getName();
        this.price = productEntity.getPrice();
        this.imageUrl = productEntity.getImageUrl();
    }
}
