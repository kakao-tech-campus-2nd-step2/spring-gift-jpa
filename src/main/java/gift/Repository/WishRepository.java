package gift.Repository;

import gift.Model.ResponseWishDTO;
import gift.Model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
    @Query("SELECT new gift.Model.ResponseWishDTO(p.name, w.count) " +
            "FROM Wish w INNER JOIN Product p ON w.productId = p.id " +
            "WHERE w.memberId = :memberId")
    List<ResponseWishDTO> findWishsByMemberId(@Param("memberId") Long memberId);

    Optional<Wish> findByMemberIdAndProductId(Long memberId, Long productId);
}
