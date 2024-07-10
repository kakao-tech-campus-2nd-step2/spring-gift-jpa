package gift.domain.wishlist.dto;

import gift.domain.wishlist.entity.WishItem;
import jakarta.validation.constraints.NotNull;

public record WishItemDto(
    Long id,

    @NotNull(message = "상품 정보를 입력해주세요.")
    Long productId)
{
    public WishItem toWishItem(Long userId) {
        return new WishItem(id, userId, productId);
    }
}
