package gift.controller;

import gift.dto.WishlistRequest;
import gift.model.Member;
import gift.service.WishlistService;
import gift.model.Product;
import gift.annotation.LoginMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping
    @ResponseBody
    public String addWishlist(@RequestBody WishlistRequest wishlistRequest,
        @LoginMember Member member) {
        if (member == null) {
            return "redirect:/members/login";
        }
        wishlistService.addWishlist(member.getEmail(), wishlistRequest.productId());
        return "위시리스트에 추가되었습니다.";
    }

    @GetMapping
    public String getWishlist(@LoginMember Member member, Model model, @PageableDefault(size = 5) Pageable pageable) {
        if (member == null) {
            return "redirect:/members/login";
        }
        Page<Product> wishlist = wishlistService.getWishlist(member.getEmail(), pageable);
        model.addAttribute("wishlist", wishlist);
        return "wishlist";
    }

    @DeleteMapping("/{productId}")
    @ResponseBody
    public String removeWishlist(@PathVariable Long productId, @LoginMember Member member) {
        if (member == null) {
            return "redirect:/members/login";
        }
        wishlistService.removeWishlist(member.getEmail(), productId);
        return "{\"status\": \"success\"}";
    }
}