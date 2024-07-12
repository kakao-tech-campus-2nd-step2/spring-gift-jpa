package gift.wishlist;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {

    List<Wishlist> findByProductId(Long productId);

    void deleteByProductId(Long productId);

    List<Wishlist> findByMemberId(Long memberId);

}
