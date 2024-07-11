package gift.dto.wish;

public record WishCreateDTO(
        long userId,
        long productId,
        int quantity
) {
}
