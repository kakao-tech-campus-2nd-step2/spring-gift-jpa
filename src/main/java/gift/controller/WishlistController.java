package gift.controller;

import gift.domain.WishlistItem;
import gift.dto.request.WishlistNameRequest;
import gift.service.WishlistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
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
    public String getWishlist(@PathVariable Long memberId, Model model, Pageable pageable) {
        Page<WishlistItem> wishlist = wishlistService.getWishlistByMemberId(memberId, pageable);
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("memberId", memberId);
        return "wishlist-list";
    }
}
