package gift.Controller;

import gift.Model.Product;
import gift.Model.Wishlist;
import gift.Service.WishlistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService){
        this.wishlistService = wishlistService;
    }

    @PostMapping()
    public void addWishlist(@RequestHeader("bearer") String token, @RequestBody Product product){
        wishlistService.add(token, product);
    }

    @DeleteMapping("/{name}")
    public void deleteWishlist(@RequestHeader("bearer") String token, @PathVariable String name){
        wishlistService.delete(token, name);
    }

    @GetMapping()
    public List<Wishlist> viewAllWishlist(@RequestHeader("bearer") String token){
        return wishlistService.viewAll(token);
    }
}
