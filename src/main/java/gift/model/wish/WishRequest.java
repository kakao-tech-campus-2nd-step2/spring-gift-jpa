package gift.model.wish;

import gift.model.product.Product;
import gift.model.user.User;
import jakarta.validation.constraints.Min;

public record WishRequest(
    Long productId,
    @Min(value = 0)
    int count) {

    public Wish toEntity(User user, Product product, int count) {
        return new Wish(null, user, product, count);
    }
}
