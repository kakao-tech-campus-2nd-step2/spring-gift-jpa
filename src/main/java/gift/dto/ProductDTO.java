package gift.dto;

public class ProductDTO {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, int price, String imageUrl) {
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


}
