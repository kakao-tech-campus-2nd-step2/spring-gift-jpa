package gift.controller;

import gift.model.Member;
import gift.model.WishList;
import gift.repository.MemberRepository;
import gift.service.MemberService;
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
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public WishlistController(WishlistService wishlistService, MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.wishlistService = wishlistService;
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/add")
    public ResponseEntity<WishList> addItem(@RequestHeader("Authorization") String token, @RequestParam Long productId) {
        Claims claims = jwtUtil.extractClaims(token.replace("Bearer ", ""));
        Long memberId = Long.parseLong(claims.getSubject());
        WishList addedItem = wishlistService.addProduct(memberId, productId);
        return ResponseEntity.ok(addedItem);
    }

    @GetMapping("/items")
    public ResponseEntity<List<WishList>> getItems(@RequestHeader("Authorization") String token) {
        Claims claims = jwtUtil.extractClaims(token.replace("Bearer ", ""));
        Long memberId = Long.parseLong(claims.getSubject());
        List<WishList> products = wishlistService.getProductsByMemberId(memberId);
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") Long productId) {
        wishlistService.deleteById(productId);
        return ResponseEntity.ok().body("delete complete!");
    }
}
