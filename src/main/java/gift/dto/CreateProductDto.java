package gift.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateProductDto {
    @NotBlank
    @Size(min = 1, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9\\s()\\[\\]+\\-&/\\_]*$", message = "허용된 특수 문자는 (, ), [, ], +, -, &, /, _ 입니다.")
    @Pattern(regexp = "^(?!.*카카오).*$", message = "\"카카오\"가 포함된 상품명은 사용할 수 없습니다.")
    String name;
    @NotBlank
    @Min(0)
    Integer price;
    @NotBlank
    String imageUrl;

    public String getName() {
        return this.name;
    }

    public Integer getPrice() {
        return this.price;
    }

    public String getImageUrl() { return this.imageUrl; }
}
