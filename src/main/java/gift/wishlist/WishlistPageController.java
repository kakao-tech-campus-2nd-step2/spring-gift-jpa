package gift.wishlist;

import gift.member.MemberTokenResolver;
import gift.product.Product;
import gift.token.MemberTokenDTO;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WishlistPageController {

    private final WishlistService wishlistService;

    public WishlistPageController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/wishlist")
    public String wishlist() {
        return "emptyWishlist";
    }

    @GetMapping("/wishlistPage")
    public String wishlistPage(@MemberTokenResolver MemberTokenDTO token, Model model) {
        List<Product> products = wishlistService.getAllWishlists(token);
        model.addAttribute("products", products);
        return "wishlist";
    }
}
