package gift.controller.wish.dto;

import gift.model.wish.Wish;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class WishRequest {

    public record AddWishRequest(
        @NotNull
        Long productId,
        @Min(1)
        Long count
    ) {

        public Wish toEntity(String userId) {
            return new Wish(null, userId, this.productId, this.count);
        }
    }

    public record UpdateWishRequest(
        @NotNull
        Long productId,
        @Min(1)
        Long count
    ) {

    }

    public record DeleteWishRequest(
        @NotNull
        Long productId
    ) {

    }
}
