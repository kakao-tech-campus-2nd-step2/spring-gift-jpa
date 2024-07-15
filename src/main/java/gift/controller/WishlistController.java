package gift.controller;

import gift.model.CurrentMember;
import gift.model.Member;
import gift.model.Wishlist;
import gift.service.WishlistService;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.net.URI;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public ResponseEntity<Page<Wishlist>> getWishlist(
            @CurrentMember Member member,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Wishlist> wishlistPage = wishlistService.getWishlist(member.getId(), pageable);
        return ResponseEntity.ok(wishlistPage);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addProductToWishlist(@CurrentMember Member member, @PathVariable Long productId) {
        wishlistService.addProductToWishlist(member.getId(), productId);
        return ResponseEntity.created(URI.create("/api/wishlist/" + productId)).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeProductFromWishlist(@CurrentMember Member member, @PathVariable Long productId) {
        wishlistService.removeProductFromWishlist(member.getId(), productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
