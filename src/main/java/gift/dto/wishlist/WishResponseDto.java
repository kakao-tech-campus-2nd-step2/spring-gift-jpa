package gift.dto.wishlist;

import gift.entity.Product;

public record WishResponseDto(
    Long id,
    Product product,
    int quantity
) {

}
