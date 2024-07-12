package gift.wishlist.presentation;

import gift.wishlist.application.WishlistResponse;
import gift.wishlist.application.WishlistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<WishlistResponse>> findAll(
            @RequestAttribute("memberId") Long memberId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                wishlistService.findAllByMemberId(memberId, pageable)
        );
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Page<WishlistResponse>> findAllByProductId(
            @PathVariable("productId") Long productId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                wishlistService.findAllByProductId(productId, pageable)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long wishlistId) {
        wishlistService.delete(wishlistId);
    }
}
