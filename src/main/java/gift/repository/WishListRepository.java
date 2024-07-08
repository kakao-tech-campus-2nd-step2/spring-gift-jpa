package gift.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gift.entity.WishList;

@Repository
public interface WishListRepository  extends JpaRepository<WishList, Long>{

    @Query("SELECT * FROM wishlist WHERE wishlist.memberId = :memberId")
    List<WishList> findProductIdsByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT wishlist.id FROM wishlist WHERE wishlist.memberId = :memberId AND wishlist.productId = :productId")
    Long findId(@Param("memberId") Long memberId, @Param("productId") Long productId);
}
