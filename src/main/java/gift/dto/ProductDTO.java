package gift.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductDTO {

    @Size(max = 15, message = "상품 이름은 최대 15자까지 입력할 수 있습니다.")
    @Pattern(
            regexp = "^[a-zA-Z0-9 ()\\[\\]+,&/_-]*$",
            message = "상품 이름에는 영문자, 숫자, 공백, (), [], +, -, &, /, _ 만 사용할 수 있습니다."
    )
    public String name;

    @NotNull(message = "상품 가격은 필수 항목입니다.")
    public Integer price;

    @NotEmpty(message = "이미지 URL은 필수 항목입니다.")
    public String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.contains("카카오")) {
            throw new IllegalArgumentException("상품 이름에 '카카오'가 포함된 경우 담당 MD와 협의가 필요합니다.");
        }
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
