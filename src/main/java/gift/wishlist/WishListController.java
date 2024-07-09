package gift.wishlist;

import gift.auth.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/wishlist")
public class WishListController {

    private static final Logger logger = Logger.getLogger(WishListController.class.getName());
    private final WishListService wishListService;
    private final JwtUtil jwtUtil;

    public WishListController(WishListService wishListService, JwtUtil jwtUtil) {
        this.wishListService = wishListService;
        this.jwtUtil = jwtUtil;
    }

    private void extractEmailFromTokenAndValidate(HttpServletRequest request,String email) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warning("Unauthorized access attempt without Bearer token");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        String token = authHeader.substring(7);
        String tokenEmail;
        try {
            tokenEmail = jwtUtil.extractEmail(token);
        } catch (Exception e) {
            logger.warning("Failed to extract email from token: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        if (!email.equals(tokenEmail)) {
            logger.warning("Forbidden access attempt with mismatched email: " + email);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이메일이 토큰과 일치하지 않습니다."); // Forbidden
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<WishListDTO>> getWishList(@PathVariable("email") String email,
        HttpServletRequest request) {
        logger.info("getWishList called with email: " + email);
        extractEmailFromTokenAndValidate(request, email);
        List<WishListDTO> wishLists = wishListService.getWishListsByEmail(email);
        logger.info("Successfully retrieved wishlist for email: " + email);
        return ResponseEntity.ok(wishLists);
    }

    @PostMapping("/{email}")
    public ResponseEntity<String> addWishList(@PathVariable("email") String email,
        HttpServletRequest request, @RequestBody WishListDTO wishListDTO) {
        logger.info("addWishList called with email: " + email);
        extractEmailFromTokenAndValidate(request, email);
        WishListDTO wishListDTO1= new WishListDTO(email, wishListDTO.getProductId(), wishListDTO.getNum());
        wishListService.addWishList(wishListDTO1);
        logger.info("Successfully added wishlist for email: " + email);
        return ResponseEntity.status(HttpStatus.CREATED).body("위시리스트에 추가되었습니다.");
    }

    @PutMapping("/{email}/{productId}")
    public ResponseEntity<String> updateWishList(@PathVariable("email") String email,
        @PathVariable("productId") long productId, HttpServletRequest request,
        @RequestBody WishListDTO wishListDTO) throws NotFoundException {
        logger.info("updateWishList called with email: " + email + " and product_id: " + productId);
        extractEmailFromTokenAndValidate(request, email);
        wishListService.updateWishList(email, productId, wishListDTO.getNum());
        logger.info("Successfully updated wishlist for email: " + email + " and product_id: " + productId);
        return ResponseEntity.ok().body("업데이트 성공!");
    }

    @DeleteMapping("/{email}/{productId}")
    public ResponseEntity<String> deleteWishList(@PathVariable("email") String email,
        @PathVariable("productId") long productId, HttpServletRequest request)
        throws NotFoundException {
        logger.info("deleteWishList called with email: " + email + " and product_id: " + productId);
        extractEmailFromTokenAndValidate(request, email);
        wishListService.deleteWishList(email, productId);
        logger.info("Successfully deleted wishlist for email: " + email + " and product_id: " + productId);
        return ResponseEntity.ok().body("삭제되었습니다.");
    }
}
