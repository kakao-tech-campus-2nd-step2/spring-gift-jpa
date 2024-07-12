package gift.controller;

import gift.model.WishList;
import gift.service.WishlistService;
import gift.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;
    private final JwtUtil jwtUtil;

    public WishlistController(WishlistService wishlistService, JwtUtil jwtUtil) {
        this.wishlistService = wishlistService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItem(@RequestHeader("Authorization") String token, @RequestBody WishList product) {
        Claims claims = jwtUtil.extractClaims(token.replace("Bearer ", ""));
        Long memberId = Long.parseLong(claims.getSubject());
        product.setMemberId(memberId);
        WishList addedItem = wishlistService.addProduct(product);
        if (addedItem.getPrice() < 0){
            ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("가격이 음수 일 수는 없습니다.");
        }
        return ResponseEntity.ok(addedItem);
    }

    @GetMapping("/items")
    public ResponseEntity<?> getItems(@RequestHeader("Authorization") String token) {
        Claims claims = jwtUtil.extractClaims(token.replace("Bearer ", ""));
        Long memberId = Long.parseLong(claims.getSubject());
        List<WishList> products = wishlistService.getProductsByMemberId(memberId);
        if (products == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("목록이 비었습니다");
        }
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        wishlistService.deleteItem(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
