package gift.repository;

import gift.entity.WishEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<WishEntity, Long> {

    List<WishEntity> findByUserEntityId(Long userId);

    Optional<WishEntity> findByUserEntityIdAndProductEntityId(Long userId, Long ProductId);
}
