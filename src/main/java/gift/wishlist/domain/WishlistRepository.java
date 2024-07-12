package gift.wishlist.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Page<Wishlist> findAllByMemberId(Long memberId, Pageable pageable);

    Page<Wishlist> findAllByProductId(Long productId, Pageable pageable);

    void deleteAllByMemberId(Long memberId);

    void deleteAllByProductId(Long productId);
}
