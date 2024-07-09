package gift.wishlist.dto;

import gift.wishlist.entity.WishList;

public record WishListReqDto(
        Long productId,
        Integer quantity
) {

    public WishList toEntity(Long memberId) {

        return new WishList(
                memberId,
                productId,
                quantity
        );
    }
}
