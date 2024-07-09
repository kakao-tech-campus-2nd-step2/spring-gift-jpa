package gift.model.product;

import gift.common.annotation.ProductNameValid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record ProductRequest(

    @NotBlank
    @Length(min = 1, max = 15, message = "상품 이름은 공백 포함 최대 15자까지 입력할 수 있습니다.")
    @Pattern(
        regexp = "^[a-zA-Z0-9가-힣\\s\\(\\)\\[\\]\\+\\-\\&\\/\\_]*$",
        message = "상품 이름에 허용되지 않은 특수 문자가 포함되어 있습니다. 허용되는 특수 문자: ( ), [ ], +, -, &, /, _"
    )
    @ProductNameValid
    String name,
    @Min(value = 0)
    int price,
    @NotBlank
    String imageUrl) {

    public Product toEntity() {
        return new Product(null, name, price, imageUrl);
    }
}
