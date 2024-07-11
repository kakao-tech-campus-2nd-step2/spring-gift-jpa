package gift.controller;

import gift.model.Product;
import gift.model.WishlistDTO;
import gift.service.ProductService;
import gift.service.WishlistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {


    private final WishlistService wishlistService;
    private final ProductService productService;

    public WishlistController(WishlistService wishlistService, ProductService productService) {
        this.wishlistService = wishlistService;
        this.productService = productService;
    }

    @GetMapping()
    public Set<Product> getWishlists(HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        return wishlistService.getWishlistProducts(email);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> postWishlist(HttpServletRequest request, @RequestBody @Valid WishlistDTO form) {
        String email = (String) request.getAttribute("email");
        wishlistService.addWishlistProduct(email, form);
        return ResponseEntity.status(HttpStatus.OK).body("Wishlist created");
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteWishlist(HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        wishlistService.deleteWishlist(email);
        return ResponseEntity.status(HttpStatus.OK).body("Wishlist deleted");
    }
}
