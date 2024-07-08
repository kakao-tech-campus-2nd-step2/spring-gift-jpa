package gift.dto.wishlist;

import gift.entity.Product;

public record WishResponse(
    Long id,
    Product product,
    int quantity
) {

}
