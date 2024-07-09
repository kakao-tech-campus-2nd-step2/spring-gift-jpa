package gift.domain;

public record WishListRequest(
        String memberId,
        Long menuId
) {

}
