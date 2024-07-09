package gift.wishlist.dto;

import gift.wishlist.WishList;

public record WishListResDto(
        Long id,
        Long productId,
        Integer quantity
) {

    public WishListResDto(WishList wishList) {
        this(wishList.getId(), wishList.getProductId(), wishList.getQuantity());
    }
}
