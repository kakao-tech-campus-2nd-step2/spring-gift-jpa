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
    public void add(@RequestAttribute("email") String memberEmail, @RequestParam Long productId) {
        wishlistService.add(memberEmail, productId);
    }

    @GetMapping("")
    public ResponseEntity<List<WishlistResponse>> findAll(@RequestAttribute("email") String memberEmail) {
        return ResponseEntity.ok(wishlistService.findAllByMember(memberEmail));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long wishlistId) {
        wishlistService.delete(wishlistId);
    }
}
