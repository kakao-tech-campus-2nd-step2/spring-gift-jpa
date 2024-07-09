package gift.repository;

import gift.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {

    @Query("SELECT w FROM Wish w LEFT JOIN FETCH Product p ON w.product.id =p.id  WHERE w.memberId = :memberId")
    List<Wish> findAllByMemberIdWithProduct(@Param("memberId") Long memberId);

    @Query("SELECT w FROM Wish w WHERE w.memberId = :memberId and w.product.id = :productId")
    Optional<Wish> findByMemberIdAndProductId(Long memberId, Long productId);

}
