package gift.Controller;

import gift.Model.Product;
import gift.Model.Wishlist;
import gift.Service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wishlist")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;

    @PostMapping()
    public void addWishlist(@RequestHeader("Bearer") String token, @RequestParam String name){
        wishlistService.add(token, name);
    }

    @DeleteMapping("/{name}")
    public void deleteWishlist(@RequestHeader("Bearer") String token, @PathVariable String name){
        wishlistService.delete(token, name);
    }

    @GetMapping()
    public List<String> viewAllWishlist(@RequestHeader("Bearer") String token){
        return wishlistService.viewAll(token);
    }
}
