package gift.controller;

import gift.common.annotation.LoginUser;
import gift.model.product.ProductListResponse;
import gift.model.user.User;
import gift.model.wish.WishDeleteRequest;
import gift.model.wish.WishListResponse;
import gift.model.wish.WishRequest;
import gift.service.UserService;
import gift.service.WishService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wish")
public class WishListController {

    private final WishService wishService;

    public WishListController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping("")
    public ResponseEntity<WishListResponse> getAllWishList(@LoginUser User user) {
        WishListResponse responses = wishService.findAllWish(user.getId());
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("")
    public ResponseEntity<String> addWishProduct(@LoginUser User user, @Valid @RequestBody WishRequest wishRequest) {
        wishService.addWistList(user.getId(), wishRequest);
        return ResponseEntity.ok().body("위시리스트에 상품이 추가되었습니다.");
    }

    @PatchMapping("")
    public ResponseEntity<String> updateWishProduct(@LoginUser User user,
        @Valid @RequestBody WishRequest wishRequest) {
        wishService.updateWishList(user.getId(), wishRequest);
        return ResponseEntity.ok().body("위시리스트에 상품이 수정되었습니다.");
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteWishProduct(@LoginUser User user, @RequestBody WishDeleteRequest wishDeleteRequest) {
        wishService.deleteWishList(user.getId(), wishDeleteRequest.productId());
        return ResponseEntity.ok().body("위시리스트에서 상품이 삭제되었습니다.");
    }
}
