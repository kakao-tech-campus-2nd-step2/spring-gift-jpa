package gift.repository;

import gift.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {

    @Query("SELECT w FROM Wish w JOIN FETCH w.product WHERE w.memberId = :memberId")
    List<Wish> findWishesByMemberIdWithProduct(@Param("memberId") Long memberId);

    @Query("SELECT w FROM Wish w WHERE w.memberId = :memberId and w.productId = :productId")
    Optional<Wish> findByMemberIdAndProductId(Long memberId, Long productId);

}
