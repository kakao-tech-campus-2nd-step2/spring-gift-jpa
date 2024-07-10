package gift.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Product {

    private Long id;

    @NotBlank(message = "Please enter the product name")
    @Size(max = 15, message = "The product name can be up to 15 characters long (including spaces)")
    @Pattern(
        regexp = "^[a-zA-Z0-9가-힣\\s\\(\\)\\[\\]\\+\\-&/_]*$",
        message = "Only the following special characters are allowed: (), [], +, -, &, /, _"
    )
    @Pattern(regexp = "^(?!.*카카오).*$", message = "The term '카카오' can only be used after consultation with the responsible MD")
    private String name;

    @Min(value = 0, message = "The product price must be zero or higher.")
    private int price;

    @Pattern(
        regexp = "^https?://[\\w.-]+(?:\\.[\\w\\.-]+)+/.*\\.(jpg|jpeg|png|gif|bmp)$",
        message = "Invalid URL format"
    )
    private String imageUrl;

    public Product(Long id, String name, int price, String imageUrl) {
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