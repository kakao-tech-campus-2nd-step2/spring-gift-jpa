package gift.doamin.wishlist.repository;

import gift.doamin.wishlist.entity.WishList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaWishListRepository extends JpaRepository<WishList, Long> {

    boolean existsByUserIdAndProductId(Long userId, Long productId);

    List<WishList> findAllByUserId(Long userId);

    Optional<WishList> findByUserIdAndProductId(Long userId, Long productId);
}
