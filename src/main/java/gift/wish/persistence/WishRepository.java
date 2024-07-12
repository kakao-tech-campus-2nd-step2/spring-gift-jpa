package gift.wish.persistence;

import gift.wish.domain.Wish;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    @Query("SELECT w FROM Wish w JOIN FETCH w.user JOIN FETCH w.product WHERE w.id = :wishId")
    Optional<Wish> findWishByIdWithUserAndProduct(Long wishId);

    @Query("SELECT w FROM Wish w JOIN FETCH w.user JOIN FETCH w.product WHERE w.user.id = :userId")
    List<Wish> findWishesByUserIdWithUserAndProduct(Long userId);
}
