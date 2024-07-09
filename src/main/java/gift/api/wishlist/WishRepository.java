package gift.api.wishlist;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, WishId> {
    @Transactional
    void deleteByMemberIdAndProductId(Long memberId, Long productId);
    List<Wish> findByMemberId(Long memberId);
}
