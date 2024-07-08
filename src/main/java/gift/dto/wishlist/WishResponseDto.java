package gift.dto.wishlist;

import gift.model.Product;

public record WishResponseDto(
    Long id,
    Product product,
    int quantity
) {

}
