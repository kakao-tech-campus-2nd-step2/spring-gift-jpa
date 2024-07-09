package gift.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ProductRequestDto {

    @NotBlank(message = "상품명은 빈 칸일 수 없습니다.")
    private final String name;

    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private final int price;

    @NotBlank(message = "이미지 URL은 빈 칸일 수 없습니다.")
    private final String imageUrl;

    public ProductRequestDto(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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
