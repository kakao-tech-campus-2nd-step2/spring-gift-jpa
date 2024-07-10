package gift.dto;

public class ProductDto {

    private final long id;
    private final String name;
    private final long price;
    private final String imageUrl;

    public ProductDto(long id, String name, long price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductDto(Product p) {
        id = p.getId();
        name = p.getName();
        price = p.getPrice();
        imageUrl = p.getImageUrl();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
