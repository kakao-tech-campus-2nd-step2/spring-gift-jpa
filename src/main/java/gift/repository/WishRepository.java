package gift.repository;

import gift.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
    @Query("SELECT w.productId FROM Wish w WHERE w.userId = :userId")
    List<Long> findProductIdsByUserId(@Param("userId") Long userId);

    void deleteByUserIdAndProductId(Long userId, Long productId);
}
