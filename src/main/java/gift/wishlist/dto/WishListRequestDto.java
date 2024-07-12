package gift.wishlist.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// 위시리스트에 넣는 요청 시 사용할 dto.
public record WishListRequestDto(
    @NotNull
    @Min(value = 1)
    long userId,

    @NotNull
    @Min(value = 1)
    long productId
) {

}
