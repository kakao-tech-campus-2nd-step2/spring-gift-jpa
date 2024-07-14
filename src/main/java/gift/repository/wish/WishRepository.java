package gift.repository.wish;

import gift.domain.wish.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByUserIdAndIsDeletedFalse(Long userId);
    Optional<Wish> findByIdAndUserId(Long wishId, Long userId);
}
