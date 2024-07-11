package gift.dto.wish;

public record WishInfoDTO(
        long id,
        long userId,
        long productId,
        int quantity
) {
}
