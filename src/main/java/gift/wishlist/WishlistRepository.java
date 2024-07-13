package gift.wishlist;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findAllByMemberEmail(String email);

    Page<Wishlist> findAllByMemberEmail(String email, Pageable pageable);

    Optional<Wishlist> findByMemberEmailAndProductId(String email, Long productId);
}
