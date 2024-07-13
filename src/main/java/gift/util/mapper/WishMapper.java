package gift.util.mapper;

import gift.dto.wish.WishResponse;
import gift.entity.Product;
import gift.entity.Wish;

public class WishMapper {

    public static WishResponse toResponse(Wish wish) {
        Product product = Product.builder()
            .id(wish.getProduct().getId())
            .name(wish.getProduct().getName())
            .price(wish.getProduct().getPrice())
            .imageUrl(wish.getProduct().getImageUrl())
            .build();
        return new WishResponse(wish.getId(), product, wish.getQuantity());
    }

}
