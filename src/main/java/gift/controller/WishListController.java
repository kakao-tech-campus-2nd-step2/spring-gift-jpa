package gift.controller;

import gift.common.annotation.LoginMember;
import gift.model.user.LoginUserDTO;
import gift.model.wishlist.WishList;
import gift.model.wishlist.WishRequest;
import gift.model.wishlist.WishResponse;
import gift.service.WishListService;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes")
public class WishListController {
    private final WishListService wishListService;
    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping
    public ResponseEntity<Page<WishResponse>> getWishList(@LoginMember LoginUserDTO member,
    @PageableDefault(size = 10, sort =  "name") Pageable pageable) {
        Page<WishResponse> wishLists = wishListService.getWishListByUserId(member.getId(), pageable);
        return ResponseEntity.ok(wishLists);
    }

    @PostMapping
    public ResponseEntity<WishResponse> addProductToWishList(@RequestBody WishRequest wishRequest, @LoginMember
    LoginUserDTO member) {
        WishResponse addedWish = wishListService.addWishList(member.getId(), wishRequest);
        return ResponseEntity.ok(addedWish);
    }

    @PostMapping("{id}")
    public ResponseEntity<WishResponse> updateQuantityToWishList(@PathVariable("id") Long wishId, Long userId, int quantity) {
        WishResponse updatedWish = wishListService.updateProductQuantity(wishId, userId, quantity);
        return ResponseEntity.ok(updatedWish);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeWishList(@PathVariable("id") Long userId, @PathVariable("product_id") Long productId, @LoginMember
    LoginUserDTO member) {
        wishListService.removeWishList(userId, productId);
        return ResponseEntity.noContent().build();
    }
}
