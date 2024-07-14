package gift.controller;

import gift.annotation.LoginMember;
import gift.domain.TokenAuth;
import gift.domain.WishlistItem;
import gift.dto.request.WishlistRequest;

import gift.service.WishlistService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/new")
    public String newWishlistItemForm(Model model, @LoginMember TokenAuth tokenAuth) {
        Long memberId = tokenAuth.getMember().getId();
        model.addAttribute("wishlistItem", new WishlistRequest());
        model.addAttribute("memberId", memberId);
        return "wishlist-add-form";
    }

    @PostMapping
    public String addToWishlist(@Valid @ModelAttribute WishlistRequest request, @LoginMember TokenAuth tokenAuth) {
        wishlistService.addItemToWishlist(request, tokenAuth.getToken());
        return "redirect:/wishlist";
    }

    @DeleteMapping("/delete/{productId}")
    public String deleteItemFromWishlist(@PathVariable Long productId, @LoginMember TokenAuth tokenAuth) {
        wishlistService.deleteItemFromWishlist(productId, tokenAuth.getToken());
        return "redirect:/wishlist";
    }

    @GetMapping()
    public String getWishlistForCurrentUser(Model model, Pageable pageable, @LoginMember TokenAuth tokenAuth) {
        Long memberId = tokenAuth.getMember().getId();
        Page<WishlistItem> wishlist = wishlistService.getWishlistByMemberId(memberId, pageable);
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("memberId", memberId);
        return "wishlist-list";
    }

}
