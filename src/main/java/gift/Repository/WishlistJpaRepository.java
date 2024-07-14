package gift.Repository;

import gift.Entity.Member;
import gift.Entity.Wishlist;
import gift.Entity.WishlistId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishlistJpaRepository extends JpaRepository<Wishlist, WishlistId> {

    List<Wishlist> findByIdUserId(long userId);

    @Query("SELECT w FROM Wishlist w WHERE w.id.userId = :userId AND w.id.productId = :productId")
    Optional<Wishlist> findByWishlistId(@Param("userId") long userId, @Param("productId") long productId);

    Page<Wishlist> findByMember(Member member, Pageable pageable);

}
