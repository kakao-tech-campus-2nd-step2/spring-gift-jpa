package gift.controller;

import gift.domain.Product;
import gift.domain.WishList;
import gift.error.UnauthorizedException;
import gift.service.WishListService;
import gift.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    private final WishListService wishListService;
    private final JwtUtil jwtUtil;

    public WishListController(WishListService wishListService, JwtUtil jwtUtil) {
        this.wishListService = wishListService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity<?> getWishListItems(
        HttpServletRequest request,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {

        String token = extractToken(request);
        Claims claims = jwtUtil.extractAllClaims(token);
        Number memberId = (Number) claims.get("id");

        if (page != null && size != null) {
            //쿼리 파라미터로 page와 size가 들어온 경우 페이지 네이션 서비스
            Page<WishList> wishLists = wishListService.getWishListItems(memberId.longValue(), page,
                size);
            return ResponseEntity.ok(wishLists);
        } else {
            //기존 서비스
            List<WishList> wishLists = wishListService.getWishListItems(memberId.longValue());
            return ResponseEntity.ok(wishLists);
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> addWishListItem(HttpServletRequest request,
        @Valid @RequestBody Product product) {
        String token = extractToken(request);
        Claims claims = jwtUtil.extractAllClaims(token);
        Number memberId = (Number) claims.get("id");
        WishList wishList = new WishList(memberId.longValue(), product.getId());
        wishListService.addWishListItem(wishList);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product added to wishlist");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteWishListItem(@PathVariable("id") Long id) {
        wishListService.deleteWishListItem(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product removed from wishlist");
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new UnauthorizedException("Invalid token");
    }

}
