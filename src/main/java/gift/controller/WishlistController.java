package gift.controller;

import gift.dto.WishlistDTO;
import gift.model.Product;
import gift.model.User;
import gift.security.LoginMember;
import gift.service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addWishlist(@PathVariable("productId") Long id, @LoginMember User user) {
        WishlistDTO wishlistDTO = new WishlistDTO(user.getEmail(), id);
        wishlistService.addWishlist(wishlistDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getWishlist(@LoginMember User user) {
        List<WishlistDTO> wishlist = wishlistService.loadWishlist(user.getEmail());
        List<Product> products = wishlistService.getProductsFromWishlist(wishlist);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable("productId") Long id, @LoginMember User user) {
        wishlistService.deleteWishlist(user.getEmail(), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}