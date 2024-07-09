package gift.controller.dto.response;

import gift.model.Wish;

import java.time.LocalDateTime;

public record WishResponse(
        Long id,
        int productCount,
        ProductResponse productResponse,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static WishResponse from(Wish wish) {
        return new WishResponse(wish.getId(),
                wish.getProductCount(),
                ProductResponse.from(wish.getProduct()),
                wish.getCreatedAt(), wish.getUpdatedAt());
    }
}
