package gift.domain.WishList;

import jakarta.validation.constraints.Min;

public record WishListRequest(
    @Min(value = 0, message = "상품 번호가 올바르지 않습니다")
    long productId
) {
}
