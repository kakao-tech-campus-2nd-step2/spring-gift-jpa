package gift.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

public record ProductOptionRequest(
        @NotNull(message = "상품은 반드시 선택되어야 합니다.")
        Long productId,
        @Length(min = 1, message = "이름의 길이는 최소 1자 이상이어야 합니다.")
        String name,
        @PositiveOrZero(message = "추가 금액은 0보다 크거나 같아야 합니다.")
        Integer additionalPrice) {
}
