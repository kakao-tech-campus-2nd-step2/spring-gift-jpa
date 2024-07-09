package gift.controller;

import gift.model.Name;
import gift.model.Product;
import gift.model.ProductDTO;
import gift.model.WishList;
import gift.service.WishListService;
import gift.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/wishlist")
public class WishListController {

    private final WishListService wishListService;
    private final ProductService productService;

    @Autowired
    public WishListController(WishListService wishListService, ProductService productService) {
        this.wishListService = wishListService;
        this.productService = productService;
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
        model.addAttribute("productId", new Long(0)); // productId만 입력받도록 변경
        return "add_product_to_wishlist";
    }

    @PostMapping("/add")
    public String addProductToWishList(@RequestBody Map<String, Long> payload, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        Long productId = payload.get("productId");
        Product product = productService.findProductById(productId); // 기존에 있는 제품을 추가하는 것이므로 productService 사용
        if (product != null) {
            wishListService.addProductToWishList(email, productId);
        }
        return "redirect:/wishlist";
    }

    @PostMapping("/remove/{productId}")
    public String removeProductFromWishList(@PathVariable Long productId, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        wishListService.removeProductFromWishList(email, productId);
        return "redirect:/wishlist";
    }
}