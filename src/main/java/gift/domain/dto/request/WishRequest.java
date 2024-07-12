package gift.domain.dto.request;

import gift.domain.entity.User;
import gift.domain.entity.Wish;
import jakarta.validation.constraints.NotNull;

public record WishRequest(@NotNull Long productId, @NotNull Long quantity) {

    public static WishRequest of(Wish wish) {
        return new WishRequest(wish.productId(), wish.quantity());
    }

    public static Wish toEntity(WishRequest dto, User user) {
        return new Wish(0L, dto.productId, user.id(), dto.quantity);
    }
}
