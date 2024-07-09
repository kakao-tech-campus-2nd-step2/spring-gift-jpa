package gift.Controller;

import gift.Annotation.LoginMemberResolver;
import gift.Model.User;
import gift.Model.WishListItem;
import gift.Service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WishListController {
    private final WishListService wishlistService;

    @Autowired
    public WishListController(WishListService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/wishlist")
    public String getWishlist(@LoginMemberResolver User user, Model model) {
        List<WishListItem> wishlist = wishlistService.getWishlist(user.getId());
        model.addAttribute("wishlist", wishlist);
        return "wishlist";
    }

    @PostMapping("/wishlist/add")
    public String addWishlistItem(@LoginMemberResolver User user, @RequestBody WishListItem wishListItem) {
        if(user == null) {
            return "redirect:/login";
        }
        wishListItem.setUserId(user.getId());
        wishlistService.addWishlistItem(wishListItem);
        return "redirect:/products";
    }

    @PostMapping("/wishlist/remove/{productId}")
    public String removeWishlistItem(@LoginMemberResolver User user, @RequestBody WishListItem wishListItem) {
        if(user == null) {
            return "redirect:/login";
        }

        wishListItem.setUserId(user.getId());
        wishlistService.removeWishlistItem(wishListItem);
        return "redirect:/wishlist";
    }
}
