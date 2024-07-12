package gift.wishlist.controller;

import gift.member.service.TokenService;
import gift.wishlist.model.WishList;
import gift.wishlist.service.WishListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public String getWishList(HttpServletRequest request, Model model) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String email = tokenService.extractEmailFromToken(token);
        Long memberId = wishListService.getMemberIdByEmail(email);

        List<WishList> wishlist = wishListService.getWishListByMemberId(memberId);
        model.addAttribute("wishlist", wishlist);
        return "list";
    }

    @GetMapping("/add")
    public String addProductForm() {
        return "add";
    }

    @PostMapping("/add")
    public String addProductToWishList(HttpServletRequest request, @RequestParam String product) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String email = tokenService.extractEmailFromToken(token);
        Long memberId = wishListService.getMemberIdByEmail(email);

        WishList wishList = wishListService.addProductToWishList(memberId, "Wish List Name", "Product Name");
        return "redirect:/wishlist";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        WishList wish = wishListService.getWishListById(id);
        model.addAttribute("wish", wish);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @RequestParam String product) {
        wishListService.updateProductInWishList(id, product);
        return "redirect:/wishlist";
    }

    @PostMapping("/delete/{id}")
    public String removeProductFromWishList(@PathVariable Long id) {
        wishListService.removeProductFromWishList(id);
        return "redirect:/wishlist";
    }

    @GetMapping("/view/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        WishList wish = wishListService.getWishListById(id);
        model.addAttribute("wish", wish);
        return "view";
    }
}