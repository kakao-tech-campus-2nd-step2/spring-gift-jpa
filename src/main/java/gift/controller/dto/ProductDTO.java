package gift.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductDTO {

    @NotEmpty(message = "Product name cannot be empty")
    @Pattern(
        regexp = "^[a-zA-Z0-9 ()\\[\\]+\\-&/_]{1,15}$",
        message = "상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있다 해당 특수문자 사용가능 : ( ), [ ], +, -, &, /, _"
    )
    private String name;
    private double price;

    private String imageUrl;

    public ProductDTO() {
    }

    public ProductDTO(String name, double price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }


    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
