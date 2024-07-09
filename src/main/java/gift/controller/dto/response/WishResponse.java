package gift.controller.dto.response;

import gift.model.Wish;

import java.time.LocalDateTime;

public record WishResponse(
        Long id,
        int productCount,

        Long productId,
        String productName,
        int productPrice,
        String productImageUrl,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static WishResponse from(Wish wish) {
        return new WishResponse(wish.getId(),
                wish.getProductCount(),
                wish.getProduct().getId(), wish.getProduct().getName(),
                wish.getProduct().getPrice(), wish.getProduct().getImageUrl(),
                wish.getCreatedAt(), wish.getUpdatedAt());
    }
}
