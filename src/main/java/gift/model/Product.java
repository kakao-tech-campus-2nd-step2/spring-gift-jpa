package gift.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;

public class Product {

    private Long id;

    @NotBlank(message = "Product name is mandatory")
    @Size(max = 15, message = "Product name can be up to 15 characters including spaces")
    @Pattern(
            regexp = "^[\\w\\s\\(\\)\\[\\]\\+\\-\\&\\/가-힣_]*$",
            message = "Product name contains invalid characters"
    )
    private String name;

    @Min(value = 0, message = "Price must be non-negative")
    private int price;

    @NotBlank(message = "Image URL is mandatory")
    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "Invalid image URL format"
    )
    private String imageUrl;

    // getters와 setters 추가
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
