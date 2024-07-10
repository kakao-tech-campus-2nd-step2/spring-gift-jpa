package gift.repository;

import gift.model.wish.Wish;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {

    boolean existsByProductIdAndUserId(Long productId, Long userId);

    List<Wish> findAllByUserId(Long userId);

    void deleteByProductIdAndUserId(Long productId, Long userId);

    Optional<Wish> findByProductIdAndUserId(Long productId, Long userId);

    void deleteByProductId(Long productId);
}
