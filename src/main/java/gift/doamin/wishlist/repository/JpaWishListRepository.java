package gift.doamin.wishlist.repository;

import gift.doamin.wishlist.entity.Wish;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaWishListRepository extends JpaRepository<Wish, Long> {

    boolean existsByUserIdAndProductId(Long userId, Long productId);

    Page<Wish> findAllByUserId(Long userId, Pageable pageable);

    Optional<Wish> findByUserIdAndProductId(Long userId, Long productId);
}
