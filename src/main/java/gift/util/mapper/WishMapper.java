package gift.mapper;

import gift.dto.wishlist.WishRequestDto;
import gift.entity.Wish;

public class WishMapper {

    public static Wish toWish(Long userId, WishRequestDto wishRequest) {
        return new Wish(null, userId, wishRequest.productId(), wishRequest.quantity());
    }

    public static Wish toWish(Long wishId, Long userId, WishRequestDto wishRequest) {
        return new Wish(wishId, userId, wishRequest.productId(), wishRequest.quantity());
    }

}
