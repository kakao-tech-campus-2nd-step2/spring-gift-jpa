package gift.repository;

import gift.domain.Wish;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishsRepository extends JpaRepository<Wish,Long> {
    void deleteByProductIdAndUserId(Long productId,Long userId);
    List<Wish> findByUserId(Long userId);
    boolean existsByUserIdAndProductId(Long userId , Long productId);
    Wish findByUserIdAndProductId(Long userId,Long productId);
}
