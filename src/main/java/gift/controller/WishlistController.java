package gift.controller;

import gift.entity.Product;
import gift.service.WishlistService;
import gift.util.JwtTokenProvider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Map<String, Object>> getWishlistItems(
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        String email = jwtTokenProvider.getEmail(token.substring(7));
        Page<Product> items = wishlistService.getWishlistByEmail(email, page, size);
        Map<String, Object> response = new HashMap<>();
        response.put("content", items.getContent());
        response.put("currentPage", items.getNumber() + 1);
        response.put("totalPages", items.getTotalPages());
        response.put("hasNext", items.hasNext());
        response.put("hasPrevious", items.hasPrevious());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteWishlistItem(@RequestHeader("Authorization") String token, @PathVariable Long productId) {
        String email = jwtTokenProvider.getEmail(token.substring(7));
        wishlistService.deleteWishlistItem(email, productId);
        return ResponseEntity.noContent().build();
    }
}
