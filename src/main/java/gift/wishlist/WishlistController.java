package gift.wishlist;

import gift.member.MemberDTO;
import gift.member.MemberResolver;
import gift.product.Product;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishes")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public List<Product> getAllWishlists(@MemberResolver MemberDTO memberDTO) {
        return wishlistService.getAllWishlists(memberDTO);
    }

    @PostMapping("/{product_id}")
    public void addWishlist(@MemberResolver MemberDTO memberDTO,
        @PathVariable(name = "product_id") long productId) {
        wishlistService.addWishlist(memberDTO, productId);
    }

    @DeleteMapping("/{product_id}")
    public void deleteWishlist(@MemberResolver MemberDTO memberDTO,
        @PathVariable(name = "product_id") long productId) {
        wishlistService.deleteWishlist(memberDTO, productId);
    }
}
