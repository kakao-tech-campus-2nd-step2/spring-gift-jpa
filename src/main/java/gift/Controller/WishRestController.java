package gift.Controller;

import gift.Service.WishService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wishlist")
public class WishRestController {
    private final WishService wishService;

    public WishRestController(WishService wishService){
        this.wishService = wishService;
    }

    @PostMapping()
    public void addWish(@RequestHeader("Bearer") String token, @RequestParam String name){
        wishService.add(token, name);
    }

    @DeleteMapping("/{name}")
    public void deleteWishlist(@RequestHeader("Bearer") String token, @PathVariable String name){
        wishService.delete(token, name);
    }

    @GetMapping()
    public List<String> viewAllWish(@RequestHeader("Bearer") String token){
        return wishService.getAll(token);
    }
}
