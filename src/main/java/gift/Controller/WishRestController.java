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
    public void addWish(@RequestAttribute("Email") String email, @RequestParam String name){
        wishService.add(email, name);
    }

    @DeleteMapping("/{name}")
    public void deleteWishlist(@RequestAttribute("Email") String email, @PathVariable String name){
        wishService.delete(email, name);
    }

    @GetMapping()
    public List<String> viewAllWish(@RequestAttribute("Email") String email){
        return wishService.getAll(email);
    }
}
