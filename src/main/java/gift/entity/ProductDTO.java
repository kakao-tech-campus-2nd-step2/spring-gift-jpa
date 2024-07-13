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

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull Integer getPrice() {
        return price;
    }

    public void setPrice(@NotNull Integer price) {
        this.price = price;
    }

    public @NotNull String getImageurl() {
        return imageurl;
    }

    public void setImageurl(@NotNull String imageurl) {
        this.imageurl = imageurl;
    }
}
