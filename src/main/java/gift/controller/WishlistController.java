package gift.controller;

import gift.domain.WishlistItem;
import gift.service.WishlistService;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public ResponseEntity<List<WishlistItem>> getAllWishlist(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(wishlistService.getAllWishlist(token));
    }

    @PostMapping
    public ResponseEntity<String> addWishlist(@RequestHeader("Authorization") String token, @RequestBody long id) {
        wishlistService.addItem(token, id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteWishlist(@RequestHeader("Authorization") String token, @RequestBody long id) {
        wishlistService.deleteItem(token, id);
        return ResponseEntity.ok("정상적으로 삭제되었습니다.");
    }

    @PutMapping
    public ResponseEntity<String> updateWishlist(@RequestHeader("Authorization") String token, @RequestBody long id, @RequestBody long num) {
        wishlistService.changeNum(token, id, num);
        return ResponseEntity.ok("상품 수량이 변경되었습니다.");
    }
}
