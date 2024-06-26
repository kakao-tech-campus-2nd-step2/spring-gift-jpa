package gift.dto;

public class ProductResponse {
    private final String name;
    private final int price;
    private final String description;

    public ProductResponse(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
