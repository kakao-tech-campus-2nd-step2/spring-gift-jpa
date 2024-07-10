package gift.repository;

import gift.domain.Wish;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface WishRepository extends JpaRepository<Wish, Long> {

    void deleteByProductIdAndUserInfoId(Long productId, Long userId);

    List<Wish> findByUserInfoId(Long userId);

    boolean existsByUserInfoIdAndProductId(Long userId, Long productId);

    Wish findByUserInfoIdAndProductId(Long userId, Long productId);
}
