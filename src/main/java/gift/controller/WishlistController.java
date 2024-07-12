package gift.controller;

import gift.model.Product;
import gift.model.Wishlist;
import gift.service.MemberService;
import gift.service.WishlistService;
import gift.util.JwtUtility;
import jakarta.validation.Valid;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.net.URI;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;
    private final MemberService memberService;

    public WishlistController(WishlistService wishlistService, MemberService memberService) {
        this.wishlistService = wishlistService;
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<Page<Wishlist>> getWishlist(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pageable) {
        String email = JwtUtility.extractEmail(authHeader, memberService);
        Page<Wishlist> wishlistPage = wishlistService.getWishList(email, pageable);
        return ResponseEntity.ok(wishlistPage);
    }

    @PostMapping
    public ResponseEntity<Void> addProductToWishlist(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                                     @RequestBody @Valid Product product) {
        String email = JwtUtility.extractEmail(authHeader, memberService);
        wishlistService.addProductToWishlist(email, product.getId());
        URI location = URI.create("/api/wishlist/" + product.getId());
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeProductFromWishlist(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                                          @PathVariable Long productId) {
        String email = JwtUtility.extractEmail(authHeader, memberService);
        wishlistService.removeProductFromWishlist(email, productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
