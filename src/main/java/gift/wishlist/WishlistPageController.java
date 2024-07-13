package gift.wishlist;

import gift.member.MemberTokenResolver;
import gift.product.Product;
import gift.token.MemberTokenDTO;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public String wishlistPage(
        @MemberTokenResolver MemberTokenDTO token,
        Model model,
        Pageable pageable
    ) {
        Page<Product> products = wishlistService.getAllWishlists(token, pageable);

        model.addAttribute("headerText", "Manage Wishlist");
        model.addAttribute("jsSrc", "../wishlistPage.js");
        model.addAttribute("products", products);
        model.addAttribute("page", pageable.getPageNumber() + 1);
        model.addAttribute("totalProductsSize", products.getTotalElements());
        model.addAttribute("currentPageProductSize", products.get().toList().size());
        model.addAttribute("pageLists",
            IntStream.range(1, products.getTotalPages() + 1).boxed().toList());

        return "basicPage";
    }
}
