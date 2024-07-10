package gift.repository;

import gift.entity.Wish;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {

    List<Wish> findByUserId(Long userId);

    Wish findByUserIdAndId(Long userId, Long wishId);

    @Transactional
    void deleteByUserIdAndId(Long userId, Long wishId);

    @Modifying
    @Transactional
    @Query("UPDATE Wish w SET w.number = :number WHERE w.user.id = :userId AND w.id = :wishId")
    void updateWishNumber(Long userId, Long wishId, int number);

}
