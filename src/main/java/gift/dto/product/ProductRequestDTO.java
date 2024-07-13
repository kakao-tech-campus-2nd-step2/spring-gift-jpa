package gift.dto.product;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

public record ProductRequestDTO(
        @Pattern(regexp =  "^[a-zA-Z0-9가-힣 ()\\[\\]+\\-&/_]{1,15}$", message = "이름은 1 ~ 15자 사이여야 하며, 특수 문자는 허용된 특수 문자만 가능합니다. " +
                "\n허용된 특수문자 : [, ], (, ), +, -, &, /, _ ")
        @Pattern(regexp = "^(?!.*카카오).*$", message = "'카카오'가 포함된 상품은 md만 추가할 수 있습니다. 담당 md와 협의해주세요.")
        @NotNull(message = "이름은 비어 있을 수 없습니다.")
        String name,

        @Max(value = Integer.MAX_VALUE, message = "가격은 " + Integer.MAX_VALUE + "를 넘을 수 없습니다.")
        @Min(value = 0, message = "가격은 음수가 될 수 없습니다.")
        @NotNull(message = "가격은 비어 있을 수 없습니다.")
        Integer price,

        @URL(message = "유효한 imageUrl을 입력하세요")
        @NotNull(message = "이미지 Url은 비어 있을 수 없습니다.")
        String imageUrl
) {
}


