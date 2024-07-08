package gift.wishlist.dao;

import gift.wishlist.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishesRepository extends JpaRepository<Wish, Long> {
    @Query("SELECT 1" +
            "FROM Wish w " +
            "WHERE w.memberId =:memberId AND w.productId =:productId")
    boolean exists(@Param("memberId") Long memberId, @Param("productId") Long productId);

    @Query("SELECT w.productId " +
            "FROM Wish w " +
            "WHERE w.memberId =:memberId")
    List<String> findByMemberId(@Param("memberId") Long memberId);

}