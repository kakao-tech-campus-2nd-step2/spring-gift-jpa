package gift.controller;

import gift.annotation.LoginMember;
import gift.model.Wishlist;
import gift.service.WishlistService;
import gift.model.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

  private final WishlistService wishlistService;

  public WishlistController(WishlistService wishlistService) {
    this.wishlistService = wishlistService;
  }

  @GetMapping
  public ResponseEntity<List<Wishlist>> getWishlist(@LoginMember Member member) {
    List<Wishlist> wishlist = wishlistService.getWishlist(member.getId());
    return ResponseEntity.ok(wishlist);
  }

  @PostMapping
  public ResponseEntity<Wishlist> addWishlistItem(@RequestBody Wishlist wishlist, @LoginMember Member member) {
    wishlist.setMemberId(member.getId());
    Wishlist savedWishlist = wishlistService.addWishlistItem(wishlist);
    return ResponseEntity.ok(savedWishlist);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> removeWishlistItem(@PathVariable Long id) {
    wishlistService.removeWishlistItem(id);
    return ResponseEntity.noContent().build();
  }
}
