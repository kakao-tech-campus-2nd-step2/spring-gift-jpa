package gift.domain.repository;

import gift.domain.entity.Wish;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {

    List<Wish> findWishesByUserId(Long userId);

    Optional<Wish> findWishByUserIdAndProductId(Long userId, Long productId);

    void deleteByProductIdAndUserId(Long productId, Long userId);
}
