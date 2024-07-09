package gift.model;

import gift.util.NameConstraint;
import jakarta.validation.constraints.NotNull;

public class ProductDTO {

    @NameConstraint
    @NotNull
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private String imageUrl;

    public ProductDTO(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductDTO() {
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
