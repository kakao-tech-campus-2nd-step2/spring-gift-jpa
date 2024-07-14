package gift.controller;

import gift.service.JwtUtil;
import gift.service.WishlistService;
import gift.vo.Wish;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gift.service.JwtUtil.getBearerToken;

@RestController
public class WishlistController {

    private final WishlistService service;
    private final JwtUtil jwtUtil;

    public WishlistController(WishlistService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/wishlist")
    public List<Wish> getWishProductList(@RequestHeader("Authorization") String authorizationHeader) {
        String token = getBearerToken(authorizationHeader);

        Long memberId = jwtUtil.getMemberIdFromToken(token);
        return service.getWishProductList(memberId);
    }

    @PostMapping("/wishlist/{productId}")
    public ResponseEntity<Void> addToWishlist(@PathVariable("productId") Long productId, @RequestHeader("Authorization") String authorizationHeader) {
        String token = getBearerToken(authorizationHeader);
        Long memberId = jwtUtil.getMemberIdFromToken(token);

        service.addWishProduct(memberId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/wishlist/{wishProductId}")
    public ResponseEntity<Void> deleteToWishlist(@PathVariable("wishProductId") Long wishProductId, @RequestHeader("Authorization") String authorizationHeader) {
        service.deleteWishProduct(wishProductId);

        return ResponseEntity.ok().build();
    }

}
