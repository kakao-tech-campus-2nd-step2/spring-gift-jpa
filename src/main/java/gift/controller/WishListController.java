package gift.controller;

import gift.common.annotation.LoginMember;
import gift.model.user.LoginUserDTO;
import gift.model.wishlist.WishList;
import gift.model.wishlist.WishRequest;
import gift.model.wishlist.WishResponse;
import gift.service.WishListService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes")
public class WishListController {
    private final WishListService wishListService;
    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping
    public ResponseEntity<List<WishResponse>> getWishList(@LoginMember LoginUserDTO member) {
        List<WishResponse> wishLists = wishListService.getWishListByUserId(member.getId());
        return ResponseEntity.ok(wishLists);
    }

    @PostMapping
    public ResponseEntity<WishResponse> addProductToWishList(@RequestBody WishRequest wishRequest, @LoginMember
    LoginUserDTO member) {
        wishListService.addWishList(member.getId(), wishRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{id}")
    public ResponseEntity<WishResponse> updateQuantityToWishList(@PathVariable("id") Long wishId, Long userId, int quantity) {
        wishListService.updateProductQuantity(wishId, userId, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeWishList(@PathVariable("id") Long userId, @PathVariable("product_id") Long productId, @LoginMember
    LoginUserDTO member) {
        wishListService.removeWishList(userId, productId);
        return ResponseEntity.noContent().build();
    }
}