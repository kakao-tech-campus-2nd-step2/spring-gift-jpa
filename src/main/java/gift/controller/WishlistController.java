package gift.controller;

import gift.dto.WishlistRequest;
import gift.service.WishlistService;
import gift.model.Product;
import gift.model.User;
import gift.annotation.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;
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
    public String addWishlist(@RequestBody WishlistRequest wishlistRequest, @LoginUser User user) {
        if (user == null) {
            return "redirect:/members/login";
        }
        wishlistService.addWishlist(user.email(), wishlistRequest.productId());
        return "위시리스트에 추가되었습니다.";
    }

    @GetMapping
    public String getWishlist(@LoginUser User user, Model model) {
        if (user == null) {
            return "redirect:/members/login";
        }
        List<Product> wishlist = wishlistService.getWishlist(user.email());
        model.addAttribute("wishlist", wishlist);
        return "wishlist";
    }

    @DeleteMapping("/{productId}")
    @ResponseBody
    public String removeWishlist(@PathVariable Long productId, @LoginUser User user) {
        if (user == null) {
            return "redirect:/members/login";
        }
        wishlistService.removeWishlist(user.email(), productId);
        return "{\"status\": \"success\"}";
    }
}