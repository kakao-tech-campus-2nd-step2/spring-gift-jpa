package gift.repository;

import gift.domain.Wish;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface WishRepository extends JpaRepository<Wish, Long> {

    void deleteByProductIdAndUserInfoId(Long productId, Long userId);

    Page<Wish> findByUserInfoId(Long userId, Pageable pageable);

    boolean existsByUserInfoIdAndProductId(Long userId, Long productId);

    Wish findByUserInfoIdAndProductId(Long userId, Long productId);
}
