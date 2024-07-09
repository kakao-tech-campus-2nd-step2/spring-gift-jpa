package gift.dto.wish;

import gift.entity.Product;

public record WishResponse(
    Long id,
    Product product,
    Integer quantity
) {

}
