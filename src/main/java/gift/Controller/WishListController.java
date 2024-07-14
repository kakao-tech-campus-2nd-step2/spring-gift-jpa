package gift.Controller;


import gift.Model.Member;
import gift.Model.Product;

import gift.Model.Wishlist;
import gift.Service.WishlistService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class WishListController {
    private final WishlistService wishlistService;

    public WishListController(WishlistService wishlistService){
        this.wishlistService = wishlistService;
    }

    @GetMapping("/api/wishlist")
    public String getWish(HttpServletRequest request,Model model, Pageable pageable) {
        String email = (String) request.getAttribute("email");
        wishlistService.checkUserByMemberEmail(email);
        model.addAttribute("products", wishlistService.getAllProducts(pageable));
        model.addAttribute("wishlists", wishlistService.getAllWishlist(email, pageable));

        return "wish";
    }

    @PostMapping("/api/wishlist")
    public String getWishLists(Model model) {
        return "redirect:/api/wishlist";
    }

    @PostMapping("/api/wish/add/{id}")
    public String editWishForm(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        wishlistService.checkUserByMemberEmail(email);
        Member member = wishlistService.getMemberByEmail(email);
        Product product = wishlistService.getProductById(id);
        wishlistService.addWishlist(member.getId(), product.getId());
        return "redirect:/api/wish";
    }

    @PostMapping("/api/wish/delete/{id}")
    public String deleteWish(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        wishlistService.checkUserByMemberEmail(email);
        Long wishlistId = wishlistService.getWishlistId(email,id);
        wishlistService.deleteWishlist(email, id,wishlistId);
        return "redirect:/api/wish";
    }
}
