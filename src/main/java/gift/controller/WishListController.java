package gift.controller;

import gift.Login;
import gift.dto.LoginMember;
import gift.dto.WishProduct;
import gift.dto.response.WishProductsResponse;
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
    public List<WishProductsResponse> getWishList(@Login LoginMember member) {
        return wishListService.getWishList(member.getId());
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> addWishProduct(@Login LoginMember member, @PathVariable("productId") Long productId) {
        return ResponseEntity.ok(wishListService.addWishProduct(new WishProduct(member.getId(), productId)));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteWishProduct(@Login LoginMember member, @PathVariable("productId") Long productId) {
        return ResponseEntity.ok(wishListService.deleteWishProduct(new WishProduct(member.getId(), productId)));
    }
}
