package gift.Controller;

import gift.Model.DTO.ProductDTO;
import gift.Model.DTO.WishDTO;
import gift.Service.WishService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/wishlist")
public class WishlistController {
    private final WishService wishService;
    private String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBRE1JTiJ9.M5YfW43tAR9_HEvIj-1Wgvkc9b_Cg23TZgDRNBoPqdU";

    public WishlistController(WishService wishService){
        this.wishService = wishService;
        for(int i = 0; i < 100; i++){
            wishService.add(token, "appeach");
        }
    }

    @GetMapping
    public String getAllWishlist(@RequestParam(value = "page", defaultValue = "0") int page, Model model){
        List<String> dtoList = wishService.viewAll(token);
        Page<String> wishlists = wishService.transferListToPage(dtoList, page);
        model.addAttribute("wishlists", wishlists);
        return "wishlist";
    }

}
