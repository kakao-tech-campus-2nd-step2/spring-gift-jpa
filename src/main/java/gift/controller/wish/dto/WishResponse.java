package gift.controller.wish.dto;

import gift.model.wish.Wish;

public class WishResponse {

    public record Info(
        Long wishId,
        Long productId,
        String productName,
        Long count
    ) {

        public static Info from(Wish wish) {
            return new Info(
                wish.getId(),
                wish.getProduct().getId(),
                wish.getProduct().getName(),
                wish.getCount()
            );
        }
    }
}
