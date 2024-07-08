package gift.controller;

import gift.annotation.LoginUser;
import gift.dto.ProductRequestDTO;
import gift.dto.ProductsResponseDTO;
import gift.dto.WishlistResponseDTO;
import gift.model.User;
import gift.service.ProductService;
import gift.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wishes")
public class WishListController {
    @Autowired
    WishService wishService;

    @Autowired
    ProductService productService;

    @GetMapping
    public String getWishlist(@LoginUser User user, Model model) {
        WishlistResponseDTO wishProducts = wishService.getWishlist(user.getId());
        model.addAttribute("wishProducts", wishProducts);
        return "wishlist";
    }

    @GetMapping("/addWishProduct")
    public String addWishProductPage(Model model) {
        ProductsResponseDTO products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "addWishProduct"; // addWishProduct.html로 이동
    }

    @PostMapping
    public String addWishProduct(@LoginUser User user, @RequestBody ProductRequestDTO productRequestDTO) {
        wishService.addWishProduct(user.getId(), productRequestDTO.id());
        return "wishlist";
    }


    @DeleteMapping
    public String deleteWishProduct(@LoginUser User user, @RequestBody ProductRequestDTO productRequestDTO) {
        wishService.deleteWishProduct(user.getId(), productRequestDTO.id());
        return "wishlist";
    }
}
