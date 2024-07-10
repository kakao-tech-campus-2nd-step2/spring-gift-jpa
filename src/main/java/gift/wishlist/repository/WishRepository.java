package gift.wishlist.repository;

import gift.wishlist.model.Wish;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByMemberIdAndProductId(Long memberId, Long productId);
    List<Wish> findByMemberId(Long memberId);
}
