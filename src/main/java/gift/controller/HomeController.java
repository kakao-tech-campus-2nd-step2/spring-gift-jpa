package gift.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class HomeController {

    @GetMapping("/home")
    public String showHomeForm() {
        return "home";
    }

    @GetMapping("/admin/products")
    public String productListForm() {
        return "products";
    }

    @GetMapping("/wishlist")
    public String wishlistForm() {
        return "wishlist";
    }

}
