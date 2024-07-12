package gift.Repository;

import gift.Entity.Wishlist;
import gift.Entity.WishlistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishListJpaRepository extends JpaRepository<Wishlist, WishlistId> {

    List<Wishlist> findByIdUserId(long userId);

    @Query("SELECT w FROM Wishlist w WHERE w.id.userId = :userId AND w.id.productId = :productId")
    Optional<Wishlist> findByWishlistId(@Param("userId") long userId, @Param("productId") long productId);

}
