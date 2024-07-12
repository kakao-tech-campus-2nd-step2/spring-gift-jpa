package gift.repository.wishlist;

import gift.domain.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistSpringDataJpaRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByMemberId(Long memberId);
    void deleteByMemberIdAndProductId(Long memberId, Long productId);
}