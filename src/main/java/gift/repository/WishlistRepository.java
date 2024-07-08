package gift.repository;

import gift.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    boolean existsByMemberIdAndProductId(Long memberId, Long productId);

    List<Wishlist> findByMemberId(Long memberId);

}
