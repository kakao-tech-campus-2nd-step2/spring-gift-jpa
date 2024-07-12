package gift.view;

import gift.controller.ProductController;
import gift.controller.WishlistController;
import gift.model.Product;
import gift.model.WishlistDTO;
import gift.model.WishlistItem;
import gift.repository.UserRepository;
import gift.service.ProductService;
import gift.service.UserService;
import gift.service.WishlistService;
import java.util.ArrayList;
import javax.print.attribute.standard.PrinterURI;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/wishlist")
public class WishlistViewController {
    private WishlistController wishlistController;
    private ProductController productController;
    private UserService userService;
    private ProductService productService;
    private WishlistService wishlistService;

    public WishlistViewController(WishlistController wishlistController,
        ProductController productController, UserService userService, ProductService productService,
        WishlistService wishlistService) {
        this.wishlistController = wishlistController;
        this.productController = productController;
        this.userService = userService;
        this.productService = productService;
        this.wishlistService = wishlistService;
    }

    @GetMapping("{id}")
    public String showWishlist(@PathVariable("id") Long userId, Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size) {
        Page<WishlistItem> wishlists = wishlistController.getWishlist(userId, page, size).getBody();
        model.addAttribute("wishlists", wishlists);
        model.addAttribute("id", userId);
        return "wishlist";
    }

    @GetMapping("{id}/new")
    public String showAddProduct(@PathVariable("id") Long userId, Model model,
                                @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        Page<Product> products = productController.getAllProducts(page, size).getBody();
        model.addAttribute("products", products);
        model.addAttribute("userId", userId);
        System.out.println("return add_wishlist");
        return "add_wishlist";
    }
    @PostMapping("{id}/save")
    public String saveWishlist(@PathVariable Long id, @RequestBody List<WishlistDTO> wishlistDTOList) {
        List<WishlistItem> wishlistItemList = new ArrayList<>();
        for(WishlistDTO wishlistDTO : wishlistDTOList){
            WishlistItem wishlistItem = new WishlistItem();
            Long userId = wishlistDTO.getUserId();
            wishlistItem.setUser(userService.findById(userId).get());

            Long productId = wishlistDTO.getProductId();
            wishlistItem.setProduct(productService.getProductById(productId).get());

            wishlistItem.setAmount(wishlistDTO.getAmount());
            wishlistItemList.add(wishlistItem);
        }
        wishlistService.saveWishlistItems(wishlistItemList);
        return "redirect:/wishlist/" + id;
    }
}
