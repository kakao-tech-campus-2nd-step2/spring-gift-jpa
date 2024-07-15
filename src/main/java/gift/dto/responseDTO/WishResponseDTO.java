package gift.dto.responseDTO;

import gift.domain.Wish;

public record WishResponseDTO(
    Long id,
    Long userId,
    ProductResponseDTO productResponseDTO,
    Integer count) {
    public static WishResponseDTO of(Wish wish) {
        return new WishResponseDTO(
            wish.getId(),
            wish.getUser().getId(),
            ProductResponseDTO.of(wish.getProduct()),
            wish.getCount());
    }
}
