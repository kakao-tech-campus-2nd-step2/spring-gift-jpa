package gift.doamin.wishlist.repository;

import gift.doamin.wishlist.entity.Wish;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaWishListRepository extends JpaRepository<Wish, Long> {

    boolean existsByUserIdAndProductId(Long userId, Long productId);

    List<Wish> findAllByUserId(Long userId);

    Optional<Wish> findByUserIdAndProductId(Long userId, Long productId);
}
