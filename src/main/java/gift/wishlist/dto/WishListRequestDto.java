package gift.wishlist.dto;

// 위시리스트에 넣는 요청 시 사용할 dto.
public record WishListRequestDto(
    long userId,
    long productId
) {

}
