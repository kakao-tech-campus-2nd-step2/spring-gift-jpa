package gift.dto;

public class ProductRequestDto {
    public final String name;
    public final int price;
    public final String imageUrl;

    public ProductRequestDto(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}