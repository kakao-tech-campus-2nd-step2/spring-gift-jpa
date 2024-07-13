package gift.dto.wish;

import gift.entity.Wish;

public record WishResponseDTO(
        long id,
        long productId
) {
    public static WishResponseDTO from(Wish wish) {
        return new WishResponseDTO(wish.getId(), wish.getProduct().getId());
    }
}
