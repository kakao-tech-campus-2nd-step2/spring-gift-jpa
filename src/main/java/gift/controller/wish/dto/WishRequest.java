package gift.controller.wish.dto;

import gift.model.member.Member;
import gift.model.product.Product;
import gift.model.wish.Wish;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class WishRequest {

    public record Register(
        @NotNull
        Long productId,
        @Min(1)
        Long count
    ) {

        public Wish toEntity(Member member, Product product) {
            return new Wish(null, member, product, count);
        }
    }

    public record Update(
        @NotNull
        Long productId,
        @Min(1)
        Long count
    ) {

    }

}
