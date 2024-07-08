package gift.controller.dto.request;

import gift.model.Wish;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record WishRequest(
        @NotNull
        Long productId,
        @NotNull
        @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
        Integer amount
) {
    public Wish toModel(Long userId) {
        return new Wish(productId(), userId, amount());
    }

    public Wish toModel(Long wishId, Long userId) {
        return new Wish(wishId, productId, userId, amount());
    }
}
