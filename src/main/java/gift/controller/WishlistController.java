package gift.controller;

import gift.domain.TokenAuth;
import gift.domain.WishlistItem;
import gift.dto.request.WishlistRequest;
import gift.service.TokenService;
import gift.service.WishlistService;
import jakarta.servlet.http.HttpServletRequest;
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

    private final TokenService tokenService;

    @Autowired
    public WishlistController(WishlistService wishlistService, TokenService tokenService) {
        this.wishlistService = wishlistService;
        this.tokenService = tokenService;
    }

    @GetMapping("/new")
    public String newWishlistItemForm(Model model, HttpServletRequest httpServletRequest) {
        TokenAuth token = getAuthVO(httpServletRequest);
        Long memberId = token.getMember().getId();
        model.addAttribute("wishlistItem", new WishlistRequest());
        model.addAttribute("memberId", memberId);
        return "wishlist-add-form";
    }

    @PostMapping
    public String addToWishlist(@Valid @RequestBody WishlistRequest request, HttpServletRequest httpServletRequest) {
        TokenAuth token = getAuthVO(httpServletRequest);
        wishlistService.addItemToWishlist(request, token.getToken());
        return "redirect:/wishlist";
    }

    @DeleteMapping("/delete/{productId}")
    public String deleteItemFromWishlist(@PathVariable Long productId, HttpServletRequest httpServletRequest) {
        TokenAuth token = getAuthVO(httpServletRequest);
        wishlistService.deleteItemFromWishlist(productId, token.getToken());
        return "redirect:/wishlist";
    }

    @GetMapping()
    public String getWishlistForCurrentUser(Model model, Pageable pageable, HttpServletRequest httpServletRequest) {
        TokenAuth token = getAuthVO(httpServletRequest);
        Long memberId = token.getMember().getId();
        Page<WishlistItem> wishlist = wishlistService.getWishlistByMemberId(memberId, pageable);
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("memberId", memberId);
        return "wishlist-list";
    }

    public TokenAuth getAuthVO(HttpServletRequest httpServletRequest) {
        String key = httpServletRequest.getHeader("Authorization").substring(7);
        return tokenService.findToken(key);
    }
}
