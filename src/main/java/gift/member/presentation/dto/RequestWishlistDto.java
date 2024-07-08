package gift.member.presentation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RequestWishlistDto(
    @NotNull
    @Min(1)
    Integer count
) {
    public WishlistUpdateDto toWishListUpdateDto(Long productId) {
        return new WishlistUpdateDto(productId, count());
    }
}
