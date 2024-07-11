package gift.dto.wish;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddWishRequest(
    @NotNull Long productId,
    @Positive(message = "상품 수량은 0보다 큰 수이어야 합니다.") Integer quantity
) {

}
