package gift.product.presentation.dto;

import gift.product.business.dto.ProductRegisterDto;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

public record RequestProductDto(
    @NotBlank
    @Size(max = 15)
    @Pattern(
        regexp = "^[a-zA-Z0-9ㄱ-ㅎ가-힣 ()\\[\\]+\\-&/_]*$",
        message = "오직 문자, 공백 그리고 특수문자 (),[],+,&,-,/,_만 허용됩니다."
    )
    @Pattern(
        regexp = "(?!.*카카오).*",
        message = "카카오가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다."
    )
    String name,
    @NotNull @Min(0) Integer price,
    @Size(max = 255) String description,
    @URL String imageUrl
) {

    public ProductRegisterDto toProductRegisterDto() {
        return new ProductRegisterDto(name, description, price, imageUrl);
    }
}
