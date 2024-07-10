package gift.wishlist;

import gift.member.MemberTokenResolver;
import gift.product.Product;
import gift.token.MemberTokenDTO;
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
    public List<Product> getAllWishlists(@MemberTokenResolver MemberTokenDTO memberTokenDTO) {
        return wishlistService.getAllWishlists(memberTokenDTO);
    }

    @PostMapping("/{product_id}")
    public void addWishlist(
        @MemberTokenResolver MemberTokenDTO memberTokenDTO,
        @PathVariable(name = "product_id") long productId
    ) {
        wishlistService.addWishlist(memberTokenDTO, productId);
    }

    @DeleteMapping("/{product_id}")
    public void deleteWishlist(
        @MemberTokenResolver MemberTokenDTO memberTokenDTO,
        @PathVariable(name = "product_id") long productId
    ) {
        wishlistService.deleteWishlist(memberTokenDTO, productId);
    }
}
