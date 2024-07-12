package gift.core.domain.wishes;

import gift.core.PagedDto;
import gift.core.domain.product.Product;

import java.util.List;

public interface WishesRepository {

    void saveWish(Long userId, Long productId);

    void removeWish(Long userId, Long productId);

    boolean exists(Long userId, Long productId);

    List<Product> getWishlistOfUser(Long userId);

    PagedDto<Product> getWishlistOfUser(Long userId, Integer page, Integer size);

}
