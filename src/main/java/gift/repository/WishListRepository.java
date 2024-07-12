package gift.repository;

import gift.entity.WishListEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishListEntity, Long> {
    List<WishListEntity> findByUserId(Long userId);
    Optional<WishListEntity> findByUserIdAndProductId(Long userId, Long productId);
}
