package gift.domain.dto;

import gift.domain.entity.User;
import gift.domain.entity.Wish;
import jakarta.validation.constraints.NotNull;

public record WishRequestDto(@NotNull Long productId, @NotNull Long quantity) {

    public static WishRequestDto of(Wish wish) {
        return new WishRequestDto(wish.productId(), wish.quantity());
    }

    public static Wish toEntity(WishRequestDto dto, User user) {
        return new Wish(0L, dto.productId, user.id(), dto.quantity);
    }
}
