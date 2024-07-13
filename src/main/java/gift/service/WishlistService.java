package gift.service;

import gift.model.Member;
import gift.model.Product;
import gift.model.ProductDto;
import gift.model.Wishlist;
import gift.repository.ProductRepository;
import gift.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WishlistService {

  private final WishlistRepository wishlistRepository;
  private final MemberService memberService;
  private final ProductService productService;
  private final ProductRepository productRepository;

  @Autowired
  public WishlistService(WishlistRepository wishlistRepository, MemberService memberService, ProductService productService, ProductRepository productRepository) {
    this.wishlistRepository = wishlistRepository;
    this.memberService = memberService;
    this.productService = productService;
    this.productRepository = productRepository;
  }

  public Wishlist addWishlistItem(Wishlist wishlist) {
    Long productId = wishlist.getProduct().getId();
    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    wishlist.setProduct(product);

    return wishlistRepository.save(wishlist);
  }

  public Page<Wishlist> getWishlist(Long memberId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return wishlistRepository.findByMemberId(memberId, pageable);
  }

  public void removeWishlistItem(Long id) {
    wishlistRepository.deleteById(id);
  }
}
