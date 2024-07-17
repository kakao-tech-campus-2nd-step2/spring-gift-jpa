package gift.domain.WishList;

import gift.domain.member.Member;
import gift.domain.product.Product;
import jakarta.validation.constraints.Min;

public record WishListRequest(
    @Min(value = 0, message = "상품 번호가 올바르지 않습니다")
    long productId
) {

    public WishList toWishList(Long memberId) {
        return new WishList(new Member(memberId), new Product(productId));
    }
}
