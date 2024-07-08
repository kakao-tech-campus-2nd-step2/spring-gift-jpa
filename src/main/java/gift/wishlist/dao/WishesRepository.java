package gift.wishlist.dao;

import gift.wishlist.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishesRepository extends JpaRepository<Wish, Long> {

    boolean existsByMemberIdAndProductId(Long memberId, Long productId);

    @Query("SELECT w.productId " +
            "FROM Wish w " +
            "WHERE w.memberId =:memberId")
    List<Long> findByMemberId(@Param("memberId") Long memberId);

}