package gift.wishlist.controller;

import gift.member.error.UnauthorizedException;
import gift.member.service.MemberService;
import gift.member.util.JwtUtil;
import gift.product.model.Product;
import gift.wishlist.domain.WishList;
import gift.wishlist.service.WishListService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<WishList>> getWishListItems(HttpServletRequest request) {
        String token = extractToken(request);
        Claims claims = jwtUtil.extractAllClaims(token);
        Long memberId = wishListService.findMemberIdByEmail(String.valueOf(claims.getSubject()));

        List<WishList> wishLists = wishListService.getWishListItems(memberId);
        return ResponseEntity.ok(wishLists);
    }

    @PostMapping
    public ResponseEntity<?> addWishListItem(HttpServletRequest request, @RequestBody Product product) {
        String token = extractToken(request);
        Claims claims = jwtUtil.extractAllClaims(token);
        Long memberId = wishListService.findMemberIdByEmail(String.valueOf(claims.getSubject()));

        WishList wishList = new WishList();
        wishList.setMemberId(memberId);
        wishList.setProductName(product.getName());
        wishList.setProductPrice(product.getPrice());
        wishListService.addWishListItem(wishList);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product added to wishlist");
    }

    @DeleteMapping("/{id}")
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
