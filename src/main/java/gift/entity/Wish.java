package gift.entity;

public record Wish(
    Long id,
    Long userId,
    Long productId,
    int quantity
) {

}
