package gift.repository;

import gift.model.Wishlist;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUsername(String username);
}
