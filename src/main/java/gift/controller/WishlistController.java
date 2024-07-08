package gift.controller;

import gift.model.User;
import gift.model.WishlistItem;
import gift.repository.WishlistRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    private WishlistRepository wishlistRepository;

    public WishlistController(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<WishlistItem>> makeWishlist(@PathVariable("id") Long userId) {
        List<WishlistItem> wishlist = wishlistRepository.findListByUserId(userId);
        return new ResponseEntity<>(wishlist, HttpStatus.OK);
    }

    @PostMapping("/{id}/save")
    public ResponseEntity<List<WishlistItem>> createWishlist(@RequestBody List<WishlistItem> wishlistItems) {
        wishlistRepository.save(wishlistItems);
        return new ResponseEntity<>(wishlistItems, HttpStatus.CREATED);
    }
}
