package gift.controller;

import gift.model.Product;
import gift.model.WishlistDTO;
import gift.service.WishlistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping()
    public List<Product> getWishlists(HttpServletRequest request,
                                      @RequestParam(required = false, defaultValue = "0", value = "page") int page,
                                      @RequestParam(required = false, defaultValue = "5", value = "size") int size) {
        String email = (String) request.getAttribute("email");
        Pageable pageable = PageRequest.of(page, size);
        return wishlistService.getWishlistProducts(email, pageable).getContent();
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
