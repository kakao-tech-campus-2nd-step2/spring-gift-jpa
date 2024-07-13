package gift.repository;

import gift.model.Wishlist;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByMemberEmail(String email);
    Wishlist findByMemberEmailAndProductId(String email, Long productId);
}
