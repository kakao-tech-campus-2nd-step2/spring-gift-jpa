package gift.controller;

import gift.annotation.LoginUser;
import gift.dto.ProductRequestDTO;
import gift.dto.ProductsResponseDTO;
import gift.dto.WishRequestDTO;
import gift.dto.WishResponseDTO;
import gift.model.User;
import gift.service.ProductService;
import gift.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        WishResponseDTO wishProducts = wishService.getWishlist(user.getId());
        model.addAttribute("wishProducts", wishProducts);
        return "wishlist";
    }

    @GetMapping("/addWishProduct")
    public String addWishProductPage(@LoginUser User user, Model model) {
        ProductsResponseDTO products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "addWishProduct"; // addWishProduct.html로 이동
    }

    @PostMapping("/addWishProduct")
    public ResponseEntity<String> addWishProduct(@LoginUser User user, @RequestBody WishRequestDTO wishRequestDTO, Model model) {
        wishService.addWishProduct(user.getId(), wishRequestDTO);

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


    @DeleteMapping
    public String deleteWishProduct(@LoginUser User user, @RequestBody WishRequestDTO wishRequestDTO, Model model) {
        wishService.deleteWishProduct(user.getId(), wishRequestDTO.productId());

        WishResponseDTO wishProducts = wishService.getWishlist(user.getId());
        model.addAttribute("wishProducts", wishProducts);

        return "wishlist";
    }
}
