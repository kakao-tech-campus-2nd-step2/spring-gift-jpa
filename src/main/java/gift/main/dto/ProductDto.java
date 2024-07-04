package gift.main.dto;

import gift.main.error.ValidProductName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductDto {
    public ProductDto() {
    }

    @ValidProductName
    private String name;

    @PositiveOrZero(message = "상품 가격은 음수일 수 없습니다.")
    private int price;

    @NotBlank(message = "이미지주소를 등록해주세요.")
    private  String imageUrl;


    public ProductDto(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductDto(ProductRequest productRequest) {
        this.name = productRequest.name();
        this.price = productRequest.price();
        this.imageUrl = productRequest.imageUrl();
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
