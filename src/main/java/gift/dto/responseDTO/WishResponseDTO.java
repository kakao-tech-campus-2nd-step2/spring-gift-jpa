package gift.dto.responseDTO;

import gift.domain.Wish;

public record WishResponseDTO(Long id, Long userId, Long productId, Integer count) {
    public static WishResponseDTO of(Wish wish) {
        return new WishResponseDTO(wish.getId(), wish.getUser().getId(), wish.getProduct().getId(),
            wish.getCount());
    }
}
