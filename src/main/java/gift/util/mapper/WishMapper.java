package gift.util.mapper;

import gift.dto.wish.WishRequest;
import gift.entity.Wish;

public class WishMapper {

    public static Wish toWish(Long userId, WishRequest wishRequest) {
        return new Wish(null, userId, wishRequest.productId(), wishRequest.quantity());
    }

    public static Wish toWish(Long wishId, Long userId, WishRequest wishRequest) {
        return new Wish(wishId, userId, wishRequest.productId(), wishRequest.quantity());
    }

}
