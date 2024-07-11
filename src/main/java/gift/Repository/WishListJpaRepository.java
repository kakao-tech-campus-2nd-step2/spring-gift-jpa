package gift.Repository;

import gift.Entity.Wishlist;
import gift.Entity.WishlistId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WishListJpaRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByIdUserId(long userId);

    Optional<Wishlist> findById(WishlistId wishlistId);
}
