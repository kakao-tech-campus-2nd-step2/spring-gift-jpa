package gift.DTO;

public class ProductResonse {

    private Long id;

    private String name;

    private int price;

    private String imageUrl;

    public ProductResonse() {

    }

    public ProductResonse(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
