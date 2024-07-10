package gift.controller;

import gift.model.Product;
import gift.model.WishList;
import gift.model.WishListDTO;
import gift.repository.ProductRepository;
import gift.repository.WishlistRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    @Autowired
    public WishlistController(WishlistRepository wishlistRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    @GetMapping()
    public Set<Product> getWishlists(HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        return wishlistRepository.findByEmail(email).getProducts();
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> postWishlist(HttpServletRequest request, @RequestBody @Valid WishListDTO wishListDTO) {
        String email = (String) request.getAttribute("email");
        Product product = productRepository.findById(wishListDTO.getProductId());

        WishList wishList = wishlistRepository.findByEmail(email);
        wishList.addProduct(product);

        boolean result = wishlistRepository.save(wishList);

        if (!result) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        return ResponseEntity.status(HttpStatus.OK).body("Wishlist created");
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteWishlist(HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        boolean result = wishlistRepository.delete(email);
        if (!result) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        return ResponseEntity.status(HttpStatus.OK).body("Wishlist deleted");
    }
}
