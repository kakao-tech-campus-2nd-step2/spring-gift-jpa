package gift.wishlist.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByMemberId(Long memberId);

    void deleteAllByMemberId(Long memberId);
    void deleteAllByProductId(Long productId);
}
