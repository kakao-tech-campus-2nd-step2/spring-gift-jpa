package gift.domain.dto;

import gift.domain.entity.User;
import gift.domain.entity.Wish;

public record WishDeleteRequestDto(Long productId) {

    public static Wish toEntity(WishDeleteRequestDto dto, User user) {
        return new Wish(0L, dto.productId, user.id(), 0L);
    }
}
