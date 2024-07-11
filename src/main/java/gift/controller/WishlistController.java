package gift.controller;

import gift.model.Product;
import gift.model.User;
import gift.dto.WishlistDTO;
import gift.security.LoginMember;
import gift.service.ProductService;
import gift.service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    private final ProductService productService;

    public WishlistController(WishlistService wishlistService, ProductService productService) {
        this.wishlistService = wishlistService;
        this.productService = productService;
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
        List<Product> products = new ArrayList<>();
        for (WishlistDTO wishlistItem : wishlist) {
            products.add(productService.getProductById(wishlistItem.getProductId()));
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable("productId") Long id, @LoginMember User user) {
        wishlistService.deleteWishlist(user.getEmail(), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}