package gift.dto;

import gift.domain.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductRequestDto {
    @NotBlank(message = "상품 이름은 공백일 수 없습니다.")
    @Size(max = 15, message = "상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣 ()\\[\\]+\\-&/_]*$", message = "상품 이름에 허용되지 않은 특수 문자가 포함되어 있습니다.")
    @Pattern(regexp = "^(?!.*카카오).*$", message = "'카카오'가 포함된 상품 이름은 담당 MD와 협의한 후에 사용할 수 있습니다.")
    private String name;
    @Min(value = 1, message = "가격은 0보다 커야 합니다.")
    private int price;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public Product toEntity() {
        return new Product(this.getName(), this.getPrice(), this.getImageUrl());
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
