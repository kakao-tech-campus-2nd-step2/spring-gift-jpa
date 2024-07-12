package gift.core.domain.wishes;

import gift.core.PagedDto;
import gift.core.domain.product.Product;

import java.util.List;

public interface WishesService {

    void addProductToWishes(Long userId, Product product);

    void removeProductFromWishes(Long userId, Product product);

    List<Product> getWishlistOfUser(Long userId);

    PagedDto<Product> getWishlistOfUser(Long userId, Integer page, Integer size);

}
