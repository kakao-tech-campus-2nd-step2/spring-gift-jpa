package gift.domain.dto.request;

import gift.domain.entity.User;
import gift.domain.entity.Wish;

public record WishDeleteRequest(Long productId) {

    public static Wish toEntity(WishDeleteRequest dto, User user) {
        return new Wish(0L, dto.productId, user.id(), 0L);
    }
}
