package gift.dto;

public class CreateProductDto {
    String name;
    String description;
    Integer price;

    public Object getName() {
        return this.name;
    }

    public Object getPrice() {
        return this.price;
    }

    public Object getDescription() {
        return this.description;
    }
}
