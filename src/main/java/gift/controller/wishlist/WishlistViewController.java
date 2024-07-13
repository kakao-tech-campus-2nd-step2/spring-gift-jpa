package gift.controller.wishlist;

import gift.domain.Product;
import gift.service.MemberService;
import gift.service.WishlistService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/my/wishlist")
public class WishlistViewController {

    private final MemberService memberService;
    private final WishlistService wishlistService;

    @Autowired
    public WishlistViewController(MemberService memberService, WishlistService wishlistService) {
        this.memberService = memberService;
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public String showWishlist(
        Model model,
        @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null) {
            return "login";
        }

        if (!authHeader.startsWith("Bearer ")) {
            model.addAttribute("error", "Invalid -token");
            return "error";
        }

        String token = authHeader.substring(7); // "Bearer " 제거
        if (!memberService.validateToken(token)) {
            model.addAttribute("error", "Fail to validate token");
            return "error";
        }
        String email = memberService.extractEmailFromToken(token);
        List<Product> wishlist = wishlistService.getWishlistByEmail(email);
        model.addAttribute("wishlist", wishlist);
        return "wishlist";
    }
}