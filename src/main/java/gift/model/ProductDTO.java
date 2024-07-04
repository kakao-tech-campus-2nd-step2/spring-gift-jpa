package gift.model;

import gift.validation.KakaoValid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ProductDTO {

    @NotNull(message = "ID는 필수 입력사항 입니다.")
    private Long id;
    @Pattern(regexp = "^[A-Za-z가-힣0-9()\\[\\]\\-&/_+\\s]{1,15}$", message = "최대 공백 포함 15글자만 가능합니다. \n또한 특수 문자는 (, ), [, ], +, -, _, &, / 만 가능합니다.")
    @KakaoValid
    private String name;
    @NotNull(message = "가격은 필수 입력사항 입니다.")
    private Long price;
    @NotEmpty(message = "imageURL은 필수 입력사항 입니다.")
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
