package gift.domain.product.dto;

public class ProductResponse {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;

    public ProductResponse() {
    }

    public ProductResponse(String name, int price, String imageUrl) {
        this(null, name, price, imageUrl);
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
        return this.price;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }
}
