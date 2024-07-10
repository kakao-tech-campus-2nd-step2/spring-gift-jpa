package gift.model.wish;

import jakarta.validation.constraints.Min;

public record WishRequest(
    Long productId,
    @Min(value = 0)
    int count) {

    public Wish toEntity(Long userId, int count) {
        return new Wish(null, userId, productId, count);
    }
}
