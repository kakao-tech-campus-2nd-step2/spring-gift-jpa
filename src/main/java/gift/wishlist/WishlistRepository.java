package gift.wishlist;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findAllByMemberEmail(String email);

    boolean existsByMemberEmailAndProductId(String memberEmail, Long productId);
}
