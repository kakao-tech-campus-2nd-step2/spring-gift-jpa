package gift.Controller;

import gift.Annotation.LoginMemberResolver;
import gift.Entity.Users;
import gift.Entity.Wishlist;
import gift.Model.WishListItem;
import gift.Service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class WishListController {
    private final WishListService wishlistService;

    @Autowired
    public WishListController(WishListService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/wishlist")
    public String getWishlist(@LoginMemberResolver Users user, Model model) {
        List<Wishlist> wishlist = wishlistService.getWishlist(user.getId());
        model.addAttribute("wishlist", wishlist);
        return "wishlist";
    }

    @PostMapping("/wishlist/add")
    public String addWishlistItem(@LoginMemberResolver Users user, @RequestBody WishListItem wishListItem) {
        if(user == null) {
            return "redirect:/login";
        }

        wishListItem.setUserId(user.getId());
        wishlistService.addWishlistItem(wishListItem);
        return "redirect:/products";
    }

    @PostMapping("/wishlist/remove")
    public String removeWishlistItem(@LoginMemberResolver Users user, @RequestBody WishListItem wishListItem) {
        if(user == null) {
            return "redirect:/login";
        }

        Optional<Wishlist> wishlistOptional = wishlistService.getWishlist(user.getId()).stream()
                .filter(wishlist -> wishlist.getProductId() == wishListItem.getProductId())
                .findFirst();
        wishListItem.setUserId(user.getId());

        wishlistService.removeWishlistItem(wishListItem, wishlistOptional.get());

        return "redirect:/wishlist";
    }

}
