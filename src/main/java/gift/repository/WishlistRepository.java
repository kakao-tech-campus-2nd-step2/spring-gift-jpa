package gift.repository;

import gift.model.Wishlist;
import java.util.List;
import java.util.Optional;

public interface WishlistRepository {
  Wishlist save(Wishlist wishlist);
  List<Wishlist> findByMemberId(Long memberId);
  void deleteById(Long id);
}
