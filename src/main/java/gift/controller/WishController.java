package gift.controller;

import gift.domain.Wish;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gift.service.WishService;

import java.util.List;

@RestController
@RequestMapping("/api/wishes")
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<List<Wish>> getWishList(@RequestParam Long userId) {
        List<Wish> wishList = wishService.getWishList(userId);
        return ResponseEntity.ok(wishList);
    }

    @PostMapping
    public ResponseEntity<Void> addToWishList(@RequestParam Long userId, @RequestParam Long productId) {
        wishService.addToWishList(userId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFromWishList(@RequestParam Long userId, @RequestParam Long productId) {
        wishService.removeFromWishList(userId, productId);
        return ResponseEntity.noContent().build();
    }
}
