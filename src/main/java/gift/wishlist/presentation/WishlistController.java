package gift.wishlist.presentation;

import gift.wishlist.application.WishlistResponse;
import gift.wishlist.application.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("")
    public void add(@RequestAttribute("memberId") Long memberId, @RequestParam Long productId) {
        wishlistService.save(memberId, productId);
    }

    @GetMapping("")
    public ResponseEntity<List<WishlistResponse>> findAll(@RequestAttribute("memberId") Long memberId) {
        return ResponseEntity.ok(wishlistService.findByMemberId(memberId));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long wishlistId) {
        wishlistService.delete(wishlistId);
    }
}
