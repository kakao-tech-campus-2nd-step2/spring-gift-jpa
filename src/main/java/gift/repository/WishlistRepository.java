package gift.repository;

import gift.model.Product;
import gift.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
  List<Wishlist> findByMemberId(Long memberId);
}
