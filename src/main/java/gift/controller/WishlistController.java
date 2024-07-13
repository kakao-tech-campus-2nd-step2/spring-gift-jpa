package gift.controller;

import gift.domain.WishlistItem;
import gift.dto.request.WishlistNameRequest;
import gift.service.WishlistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping
    public void addToWishlist(@Valid @RequestBody WishlistNameRequest request, @RequestHeader("Authorization") String token) {
        wishlistService.addItemToWishlist(request, token);
    }

    @DeleteMapping("/delete/{productId}")
    public void deleteItemFromWishlist(@PathVariable Long productId, @RequestHeader("Authorization") String token) {
        wishlistService.deleteItemFromWishlist(productId, token);
    }

    @GetMapping("/member/{memberId}")
    public List<WishlistItem> getWishlist(@PathVariable Long memberId) {
        return wishlistService.getWishlistByMemberId(memberId);
    }
}
