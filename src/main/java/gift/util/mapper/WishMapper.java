package gift.util.mapper;

import gift.dto.wish.WishResponse;
import gift.entity.Wish;

public class WishMapper {

    public static WishResponse toResponse(Wish wish) {
        return new WishResponse(wish.getId(), wish.getProduct(), wish.getQuantity());
    }

}
