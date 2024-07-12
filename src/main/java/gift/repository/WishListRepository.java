package gift.repository;

import gift.entity.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishListEntity, Long> {
    Optional<WishListEntity> findByUserEntity_IdAndProductEntity_Id(Long userId, Long productId);
    List<WishListEntity> findByUserEntity_Id(Long userId);
}
