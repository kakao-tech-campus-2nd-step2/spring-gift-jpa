package gift.domain.wishlist.dto;

import gift.domain.product.entity.Product;
import gift.domain.user.entity.User;
import gift.domain.wishlist.entity.WishItem;
import jakarta.validation.constraints.NotNull;

public record WishItemDto(
    Long id,

    @NotNull(message = "상품 정보를 입력해주세요.")
    Long productId)
{
    public WishItem toWishItem(User user, Product product) {
        return new WishItem(id, user, product);
    }
}
