package gift.controller;

import gift.model.Product;
import gift.model.Wishlist;
import gift.service.MemberService;
import gift.service.WishlistService;
import gift.util.JwtUtility;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    private final WishlistService wishListService;
    private final MemberService memberService;

    public WishlistController(WishlistService wishListService, MemberService memberService) {
        this.wishListService = wishListService;
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<Wishlist>> getWishList(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String email = JwtUtility.extractEmail(authHeader, memberService);
        List<Wishlist> wishList = wishListService.getWishList(email);
        return ResponseEntity.ok(wishList);
    }

    @PostMapping
    public ResponseEntity<Void> addProductToWishList(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody @Valid Product product) {
        String email = JwtUtility.extractEmail(authHeader, memberService);
        wishListService.addProductToWishList(email, product.getId());
        URI location = URI.create("/api/wishlist/" + product.getId());
        return ResponseEntity.created(location).build();
    }//DTO클래스 사용하도록 나중에 리팩터링할 것!!

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeProductFromWishList(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable Long productId) {
        String email = JwtUtility.extractEmail(authHeader, memberService);
        wishListService.removeProductFromWishList(email, productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
