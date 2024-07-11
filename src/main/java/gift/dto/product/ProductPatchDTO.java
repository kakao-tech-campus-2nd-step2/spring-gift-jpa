package gift.dto.product;

import gift.entity.Product;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;

public record ProductPatchDTO(
        @Nullable
        @Pattern(regexp =  "^[a-zA-Z0-9가-힣 ()\\[\\]+\\-&/_]{1,15}$", message = "이름은 1 ~ 15자 사이여야 하며, 특수 문자는 허용된 특수 문자만 가능합니다. " +
                "\n허용된 특수문자 : [, ], (, ), +, -, &, /, _ ")
        @Pattern(regexp = "^(?!.*카카오).*$", message = "'카카오'가 포함된 상품은 md만 추가할 수 있습니다. 담당 md와 협의해주세요.")
        Integer price,

        @Nullable
        @Max(value = Integer.MAX_VALUE, message = "가격은 " + Integer.MAX_VALUE + "를 넘을 수 없습니다.")
        @Min(value = 0, message = "가격은 음수가 될 수 없습니다.")
        String name,

        @Nullable
        @URL(message = "유효한 imageUrl을 입력하세요")
        String imageUrl
) {
}
