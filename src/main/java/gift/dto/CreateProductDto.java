package gift.dto;

public class CreateProductDto {
    String name;
    String description;
    Integer price;
    String imageUrl;

    public String getName() {
        return this.name;
    }

    public Integer getPrice() {
        return this.price;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImageUrl() { return this.imageUrl; }
}
