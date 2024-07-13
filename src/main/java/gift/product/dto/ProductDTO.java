package gift.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO {
    @NotBlank(message = "상품명은 필수 입력 요소입니다.")
    @Size(max = 15, message = "입력 가능한 상품명은 공백 포함 최대 15자 입니다.")
    private String name;
    @Positive(message = "상품 가격은 1 이상의 양수만 입력이 가능합니다.")
    private int price;
    @NotBlank(message = "상품 이미지 URL은 필수 입력 요소입니다.")
    private String imageUrl;

    public ProductDTO() {}

    public ProductDTO(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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
