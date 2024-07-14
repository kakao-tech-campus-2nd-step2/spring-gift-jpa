package gift.repository;

import gift.domain.Wish;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaWishRepository extends JpaRepository<Wish, Long> {
    @Query("SELECT w FROM Wish w JOIN FETCH w.product p WHERE w.user.id = :userId")
    List<Wish> findAllByUserId(Long userId);
}
