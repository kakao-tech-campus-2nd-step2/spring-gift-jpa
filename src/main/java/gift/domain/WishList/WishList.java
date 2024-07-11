package gift.domain.WishList;

import gift.domain.product.Product;
import java.util.ArrayList;

public record WishList(
    long id,
    long userId,
    ArrayList<Product> products
) {

}
