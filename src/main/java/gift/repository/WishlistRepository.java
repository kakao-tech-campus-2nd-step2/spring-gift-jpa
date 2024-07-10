package gift.repository;

import gift.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
  List<Wishlist> findByMemberId(Long memberId);
}
