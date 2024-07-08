package gift.repository;

import gift.domain.Wish;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishsRepository extends JpaRepository<Wish,Long> {
    void deleteByProductIdaAndUserId(Long productId,Long userId);
    List<Wish> findByUserId(Long userId);
    boolean existsByUserIdAndProductId(Long userId , Long productId);
    Wish findByUserIdAndProductId(Long userId,Long productId);
}
