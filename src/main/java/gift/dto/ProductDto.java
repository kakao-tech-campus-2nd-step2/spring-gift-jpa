package gift.dto;

public class ProductDto{
    private String name;
    private int price;
    private String imageUrl;

    public ProductDto(){}
    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public int getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
