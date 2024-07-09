package gift.api.wishlist;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishList, WishListId> {
    @Transactional
    void deleteByMemberIdAndProductId(Long memberId, Long productId);
    List<WishList> findByMemberId(Long memberId);
}
