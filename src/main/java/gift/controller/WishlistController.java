package gift.controller;

import gift.model.WishList;
import gift.model.WishListDTO;
import gift.repository.WishlistRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistRepository wishlistRepository;

    public WishlistController(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @GetMapping()
    public List<WishList> getWishlists(HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        return wishlistRepository.getMyWishlists(email);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> postWishlist(HttpServletRequest request, @RequestBody @Valid WishListDTO wishListDTO) {
        String email = (String) request.getAttribute("email");
        boolean result = wishlistRepository.addWishlist(email, wishListDTO);
        if (!result) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        return ResponseEntity.status(HttpStatus.OK).body("Wishlist created");
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<String> putWishlist(HttpServletRequest request, @RequestBody @Valid WishListDTO wishListDTO) {
        String email = (String) request.getAttribute("email");
        boolean result = wishlistRepository.updateWishlist(email, wishListDTO);
        if (!result) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        return ResponseEntity.status(HttpStatus.OK).body("Wishlist updated");
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteWishlist(HttpServletRequest request, @RequestBody @Valid WishListDTO wishListDTO) {
        String email = (String) request.getAttribute("email");
        boolean result = wishlistRepository.removeWishlist(email, wishListDTO.getProductId());
        if (!result) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        return ResponseEntity.status(HttpStatus.OK).body("Wishlist deleted");
    }
}
