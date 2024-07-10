package gift.wish.persistence;

import gift.wish.domain.Wish;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findByIdAndUserId(Long id, Long userId);

    List<Wish> findWishesByUserId(Long userId);
}
