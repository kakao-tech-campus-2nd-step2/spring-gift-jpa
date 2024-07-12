package gift.domain.dto.request;

import gift.domain.entity.User;
import gift.domain.entity.Wish;

public record WishDeleteRequest(Long productId) {

    public Wish toEntity(User user) {
        return new Wish(productId, user.getId(), 0L);
    }
}
