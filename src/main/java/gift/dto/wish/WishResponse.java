package gift.dto.wish;

import gift.model.Member;
import gift.model.Product;

public record WishResponse(
    Long id,
    Member member,
    Product product
) {

}
