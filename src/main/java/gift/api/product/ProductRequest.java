package gift.api.product;

import gift.api.product.validator.NoKakao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductRequest {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max = 15, message = "Must be at least 1 character, no more than 15 characters long")
    @Pattern(regexp = "^[\\w\\s가-힣()\\[\\]+\\-&/]+$", message = "Only (), [], +, -, &, / of special characters available")
    @NoKakao
    private String name;
    @NotNull(message = "Price is mandatory")
    private Integer price;
    private String imageUrl;

    public ProductRequest() {
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

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}