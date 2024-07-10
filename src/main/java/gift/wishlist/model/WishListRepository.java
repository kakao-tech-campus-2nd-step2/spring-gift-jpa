package gift.wishlist.model;

import gift.wishlist.model.dto.Wish;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<Wish, Long> {
    List<Wish> findWishesByUserIdAndIsActiveTrue(@Param("userId") Long userId);

    Optional<Wish> findByIdAndUserIdAndIsActiveTrue(Long id, Long userId);
}
