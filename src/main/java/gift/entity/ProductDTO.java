package gift.entity;

import gift.validation.constraint.NameConstraint;
import jakarta.validation.constraints.NotNull;

public class ProductDTO {

    @NameConstraint
    @NotNull
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private String imageurl;

    public ProductDTO(String name, Integer price, String imageurl) {
        this.name = name;
        this.price = price;
        this.imageurl = imageurl;
    }

    public ProductDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
