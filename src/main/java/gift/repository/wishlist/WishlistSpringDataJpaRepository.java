package gift.repository.wishlist;

import gift.domain.WishlistItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistSpringDataJpaRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByMemberId(Long memberId);
    Page<WishlistItem> findByMemberId(Long memberId, Pageable pageable);
    void deleteByMemberIdAndProductId(Long memberId, Long productId);
}