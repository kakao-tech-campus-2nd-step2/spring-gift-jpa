package gift.Repository;

import gift.Model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByMemberId(Long memberId);
    Wishlist findByMemberIdAndProductId(Long memberId, Long productId);
}
