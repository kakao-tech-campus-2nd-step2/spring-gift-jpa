package gift.dto.wishlist;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record WishRequestDto(
    @NotNull Long productId,
    @Positive(message = "상품 수량은 0보다 큰 수이어야 합니다.") int quantity
) {}
