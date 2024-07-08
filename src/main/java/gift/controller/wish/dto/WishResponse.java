package gift.controller.wish.dto;

import gift.model.product.Product;
import gift.model.wish.Wish;
import java.util.List;

public class WishResponse {

    public record WishListResponse(
        Long wishId,
        Long productId,
        String productName,
        Long count
    ) {

        public static WishListResponse from(Wish wish) {
            return new WishListResponse(
                wish.getId(),
                wish.getProduct().getId(),
                wish.getProduct().getName(),
                wish.getCount()
            );
        }
    }
}
