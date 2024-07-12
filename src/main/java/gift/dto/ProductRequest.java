package gift.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ProductRequest {
    @NotEmpty
    @Size(max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9()\\[\\]+\\-&/_ ]+$", message = "Invalid characters in name")
    private String name;

    @NotNull
    private Integer price;

    @NotEmpty
    private String imageUrl;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
