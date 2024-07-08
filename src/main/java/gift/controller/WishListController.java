package gift.controller;

import gift.Login;

import gift.domain.Product;
import gift.dto.LoginMember;
import gift.dto.WishProduct;
import gift.service.WishListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishes")
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping
    public List<Product> getWishList(@Login LoginMember member) {
        return wishListService.getWishList(member.getEmail());
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> addWishProduct(@Login LoginMember member, @PathVariable("productId") Long productId) {
        return ResponseEntity.ok(wishListService.addWishProduct(new WishProduct(member.getEmail(), productId)));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteWishProduct(@Login LoginMember member, @PathVariable("productId") Long productId) {
        return ResponseEntity.ok(wishListService.deleteWishProduct(new WishProduct(member.getEmail(), productId)));
    }
}
