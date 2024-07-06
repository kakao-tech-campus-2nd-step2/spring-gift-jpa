package gift.member.presentation.dto;

import gift.member.business.dto.WishListDto;

public record ResponseWishListDto(
    Long id,
    Long productId,
    String productName,
    Integer price,
    String imageUrl,
    Integer count
) {

    public static ResponseWishListDto from(WishListDto wishListDto) {
        return new ResponseWishListDto(
            wishListDto.id(),
            wishListDto.productId(),
            wishListDto.productName(),
            wishListDto.price(),
            wishListDto.imageUrl(),
            wishListDto.count()
        );
    }
}
