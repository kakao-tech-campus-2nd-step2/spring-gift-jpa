package gift.service;

import gift.model.Member;
import gift.model.Product;
import gift.model.Wishlist;
import gift.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

  private final WishlistRepository wishlistRepository;
  private final MemberService memberService;
  private final ProductService productService;

  @Autowired
  public WishlistService(WishlistRepository wishlistRepository, MemberService memberService, ProductService productService) {
    this.wishlistRepository = wishlistRepository;
    this.memberService = memberService;
    this.productService = productService;
  }

  public Wishlist addWishlistItem(Wishlist wishlist) {
    Long productId = wishlist.getProduct().getId();
    Product product = productService.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    wishlist.setProduct(product);

    return wishlistRepository.save(wishlist);
  }

  public List<Wishlist> getWishlist(Long memberId) {
    return wishlistRepository.findByMemberId(memberId);
  }

  public void removeWishlistItem(Long id) {
    wishlistRepository.deleteById(id);
  }
}
