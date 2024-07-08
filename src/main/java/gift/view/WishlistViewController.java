package gift.view;

import gift.controller.ProductController;
import gift.controller.WishlistController;
import gift.model.Product;
import gift.model.WishlistItem;
import javax.print.attribute.standard.PrinterURI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishlistViewController {
    private WishlistController wishlistController;
    private ProductController productController;

    public WishlistViewController(WishlistController wishlistController, ProductController productController) {
        this.wishlistController = wishlistController;
        this.productController = productController;
    }

    @GetMapping("{id}")
    public String showWishlist(@PathVariable("id") Long userId, Model model) {
        List<WishlistItem> wishlists = wishlistController.makeWishlist(userId).getBody();
        model.addAttribute("wishlists", wishlists);
        model.addAttribute("id", userId);
        return "wishlist";
    }

    @GetMapping("{id}/new")
    public String showAddProduct(@PathVariable("id") Long userId, Model model) {
        List<Product> products = productController.getAllProducts().getBody();
        model.addAttribute("products", products);
        model.addAttribute("userId", userId);
        return "add_wishlist";
    }
    @PostMapping("{id}/save")
    public String saveWishlist(@PathVariable Long id, @RequestBody List<WishlistItem> wishlistItems) {
        wishlistController.createWishlist(wishlistItems);
        return "redirect:/wishlist/" + id;
    }
}
