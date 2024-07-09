package gift.doamin.wishlist.repository;

import gift.doamin.wishlist.entity.WishList;
import java.util.List;

public interface WishListRepository {

    WishList save(WishList wishList);

    List<WishList> findByUserId(Long userId);

    WishList findByUserIdAndProductId(Long userId, Long productId);

    void update(WishList wishList);

    void deleteById(Long id);

    boolean existsByUserIdAndProductId(Long userId, Long productId);

}
