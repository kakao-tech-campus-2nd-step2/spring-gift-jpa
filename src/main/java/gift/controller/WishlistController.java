package gift.controller;

import gift.annotation.LoginMember;
import gift.model.Wishlist;
import gift.model.Product;
import gift.service.WishlistService;
import gift.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

  private final WishlistService wishlistService;

  public WishlistController(WishlistService wishlistService) {
    this.wishlistService = wishlistService;
  }

  @GetMapping
  public ResponseEntity<Page<Wishlist>> getWishlist(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @LoginMember Member member) {
    Page<Wishlist> wishlist = wishlistService.getWishlist(member.getId(), page, size);
    return ResponseEntity.ok(wishlist);
  }

  @PostMapping
  public ResponseEntity<Wishlist> addWishlistItem(@RequestBody Map<String, Object> body, @LoginMember Member member) {
    Wishlist savedWishlist = wishlistService.addWishlistItem(member, body);
    return ResponseEntity.ok(savedWishlist);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> removeWishlistItem(@PathVariable Long id) {
    wishlistService.removeWishlistItem(id);
    return ResponseEntity.noContent().build();
  }
}
