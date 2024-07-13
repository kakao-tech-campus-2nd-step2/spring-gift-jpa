package gift.controller;

import gift.model.Product;
import gift.service.WishlistService;
import gift.util.JwtTokenProvider;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public WishlistController(WishlistService wishlistService, JwtTokenProvider jwtTokenProvider) {
        this.wishlistService = wishlistService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getWishlistItems(@RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getEmail(token.substring(7));
        List<Product> items = wishlistService.getWishlistByEmail(email);
        return ResponseEntity.ok(items);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteWishlistItem(@RequestHeader("Authorization") String token, @PathVariable Long productId) {
        String email = jwtTokenProvider.getEmail(token.substring(7));
        wishlistService.deleteWishlistItem(email, productId);
        return ResponseEntity.noContent().build();
    }
}
