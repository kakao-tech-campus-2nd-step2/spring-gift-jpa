package gift.controller;

import gift.model.Name;
import gift.model.Product;
import gift.model.ProductDTO;
import gift.model.WishList;
import gift.service.WishListService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishlist")
public class WishListController {

    private final WishListService wishListService;

    @Autowired
    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping
    public String viewWishList(HttpServletRequest request, Model model) {
        String email = (String) request.getAttribute("email");
        if (email == null) {
            return "redirect:/users/login";
        }
        WishList wishList = wishListService.getWishListByUser(email);
        if (wishList == null) {
            wishList = new WishList();
            wishList.setProducts(new ArrayList<>());
        }
        model.addAttribute("wishList", wishList);
        return "wishlist";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new ProductDTO());
        return "add_product_to_wishlist";
    }

    @PostMapping("/add")
    public String addProductToWishList(@ModelAttribute ProductDTO productDTO, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        Product product = new Product(productDTO.getId(), new Name(productDTO.getName().getName()), productDTO.getPrice(), productDTO.getImageUrl());
        wishListService.addProductToWishList(email, product);
        return "redirect:/wishlist";
    }

    @PostMapping("/remove/{productId}")
    public String removeProductFromWishList(@PathVariable Long productId, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        wishListService.removeProductFromWishList(email, productId);
        return "redirect:/wishlist";
    }
}