package gift.web.dto;

import gift.domain.wish.Wish;

public record WishDto(
    Long productId,
    Long count
    ) {

    public static WishDto from(Wish wish) {
        return new WishDto(wish.productId(), wish.count());
    }

    public static Wish toEntity(WishDto dto) {
        return new Wish(null, null, dto.productId(), dto.count());
    }
    public static Wish toEntity(WishDto dto, String email) {
        return new Wish(null, email, dto.productId(), dto.count());
    }
}
