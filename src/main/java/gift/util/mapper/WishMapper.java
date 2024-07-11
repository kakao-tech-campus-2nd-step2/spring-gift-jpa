package gift.util.mapper;

import gift.dto.wish.WishResponse;
import gift.entity.Product;
import gift.entity.Wish;

public class WishMapper {

    public static WishResponse toResponse(Wish wish) {
        Product product = Product.builder()
            .id(wish.product().id())
            .name(wish.product().name())
            .price(wish.product().price())
            .imageUrl(wish.product().imageUrl())
            .build();
        return new WishResponse(wish.id(), product, wish.quantity());
    }

}
