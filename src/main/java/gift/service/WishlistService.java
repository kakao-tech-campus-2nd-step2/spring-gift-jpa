package gift.service;

import gift.model.Wishlist;
import gift.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

  private final WishlistRepository wishlistRepository;

  public WishlistService(WishlistRepository wishlistRepository) {
    this.wishlistRepository = wishlistRepository;
  }

  public Wishlist addWishlistItem(Wishlist wishlist) {
    return wishlistRepository.save(wishlist);
  }

  public List<Wishlist> getWishlist(Long memberId) {
    return wishlistRepository.findByMemberId(memberId);
  }

  public void removeWishlistItem(Long id) {
    wishlistRepository.deleteById(id);
  }
}
