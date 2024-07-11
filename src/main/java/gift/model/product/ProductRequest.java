package gift.model.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductRequest(
    Long id,
    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Size(max = 15, message = "이름의 최대 글자수는 15입니다.")
    @Pattern(
        regexp = "^[가-힣a-zA-Z0-9\\(\\)\\[\\]\\+\\-\\&\\/\\_\\s]*$",
        message = "상품 이름은 최대 15자, 한글과 영문, 그리고 특수기호([],(),+,-,&,/,_)만 사용 가능합니다!"
    )
    @Pattern(
        regexp = "(?!.*카카오).*",
        message = "'카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다!"
    )
    String name,
    int price,
    String imageUrl
) {
    public Product toEntity() {
        return new Product(name, price, imageUrl);
    }

}
