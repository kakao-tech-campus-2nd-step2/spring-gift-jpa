package gift.controller;

import gift.dto.WishResponse;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.service.WishlistService;
import gift.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wishes")
public class WishlistController {

    private final WishlistService wishlistService;
    private final JwtUtil jwtUtil;

    @Autowired
    public WishlistController(WishlistService wishlistService, JwtUtil jwtUtil) {
        this.wishlistService = wishlistService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItem(@RequestHeader("Authorization") String token, @RequestBody Map<String, Object> requestBody) {
        Claims claims = jwtUtil.extractClaims(token.replace("Bearer ", ""));
        Long memberId = Long.parseLong(claims.getSubject());

        Long productId = Long.valueOf(requestBody.get("productId").toString());
        int productNumber = Integer.parseInt(requestBody.get("productNumber").toString());

        Member member = new Member();
        member.setId(memberId);
        Product product = new Product();
        product.setId(productId);

        Wish wish = new Wish();
        wish.setMember(member);
        wish.setProduct(product);
        wish.setProductNumber(productNumber);

        Wish addedWish = wishlistService.addProduct(wish);
        return ResponseEntity.ok(new WishResponse(addedWish.getId(), addedWish.getProduct().getId(), addedWish.getProduct().getName(), addedWish.getProductNumber()));
    }

    @GetMapping("/items")
    public ResponseEntity<List<WishResponse>> getItems(@RequestHeader("Authorization") String token) {
        Claims claims = jwtUtil.extractClaims(token.replace("Bearer ", ""));
        Long memberId = Long.parseLong(claims.getSubject());

        List<WishResponse> wishes = wishlistService.getWishesByMemberId(memberId);
        return ResponseEntity.ok(wishes);
    }

    @GetMapping("/item-details/{productId}")
    public ResponseEntity<?> getProductDetails(@PathVariable Long productId) {
        try {
            Product product = wishlistService.getProductById(productId);
            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Product not found");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProductNumber(@PathVariable Long id, @RequestBody int productNumber) {
        try {
            wishlistService.updateProductNumber(id, productNumber);
            return ResponseEntity.ok("Successfully updated product quantity.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Product not found.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        try {
            wishlistService.deleteItem(id);
            return ResponseEntity.ok("Successfully deleted product.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Product not found.");
        }
    }
}
