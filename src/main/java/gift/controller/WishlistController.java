package gift.controller;

import gift.model.WishlistItem;
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

    @GetMapping("/{id}")
    public ResponseEntity<List<WishlistItem>> getWishlist(@PathVariable("id") Long userId) {
        List<WishlistItem> wishlist = wishlistService.getWishlistByUserId(userId);
        return new ResponseEntity<>(wishlist, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<List<WishlistItem>> createWishlist(@RequestBody List<WishlistItem> wishlistItems) {
        List<WishlistItem> savedItems = wishlistService.saveWishlistItems(wishlistItems);
        return new ResponseEntity<>(savedItems, HttpStatus.CREATED);
    }
}
