package gift.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;

public record Product(
        Long id,

        @NotBlank(message = "상품 이름을 입력해주세요.")
        @Size(max = 15, message = "상품 이름은 15자 이내여야 합니다.")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9\\s\\(\\)\\[\\]+&\\-/_]*$", message = "상품 이름에 유효하지 않은 문자가 포함되어 있습니다.")
        String name,

        @Min(value = 0, message = "상품 가격은 0원 이상이어야 합니다.")
        int price,
        String imageUrl
) {
}