package gift.controller;

import gift.Login;
import gift.dto.LoginMember;
import gift.dto.WishProduct;
import gift.dto.response.WishProductsResponse;
import gift.service.WishListService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishes")
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping
    public ResponseEntity<Page<WishProductsResponse>> getWishList(@Login LoginMember member,
                                                                  @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        Page<WishProductsResponse> wishes = wishListService.getWishList(member.getId(), page);
        return ResponseEntity.ok(wishes);
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
