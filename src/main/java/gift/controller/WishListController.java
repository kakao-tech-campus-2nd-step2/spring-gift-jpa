package gift.controller;

import gift.common.annotation.LoginUser;
import gift.model.product.ProductListResponse;
import gift.model.user.LoginUserRequest;
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
<<<<<<< HEAD
    public ResponseEntity<WishListResponse> getAllWishList(@LoginUser LoginUserRequest user) {
        WishListResponse responses = wishService.findAllWish(user.id());
=======
    public ResponseEntity<WishListResponse> getAllWishList(@LoginUser User user) {
        WishListResponse responses = wishService.findAllWish(user.getId());
>>>>>>> 74cf6b2bfb66a871340d58a87b34a502f4ead750
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("")
<<<<<<< HEAD
    public ResponseEntity<String> addWishProduct(@LoginUser LoginUserRequest user, @Valid @RequestBody WishRequest wishRequest) {
        wishService.addWistList(user.id(), wishRequest);
=======
    public ResponseEntity<String> addWishProduct(@LoginUser User user, @Valid @RequestBody WishRequest wishRequest) {
        wishService.addWistList(user.getId(), wishRequest);
>>>>>>> 74cf6b2bfb66a871340d58a87b34a502f4ead750
        return ResponseEntity.ok().body("위시리스트에 상품이 추가되었습니다.");
    }

    @PatchMapping("")
<<<<<<< HEAD
    public ResponseEntity<String> updateWishProduct(@LoginUser LoginUserRequest user,
        @Valid @RequestBody WishRequest wishRequest) {
        wishService.updateWishList(user.id(), wishRequest);
=======
    public ResponseEntity<String> updateWishProduct(@LoginUser User user,
        @Valid @RequestBody WishRequest wishRequest) {
        wishService.updateWishList(user.getId(), wishRequest);
>>>>>>> 74cf6b2bfb66a871340d58a87b34a502f4ead750
        return ResponseEntity.ok().body("위시리스트에 상품이 수정되었습니다.");
    }

    @DeleteMapping("")
<<<<<<< HEAD
    public ResponseEntity<String> deleteWishProduct(@LoginUser LoginUserRequest user, @RequestBody WishDeleteRequest wishDeleteRequest) {
        wishService.deleteWishList(user.id(), wishDeleteRequest.productId());
=======
    public ResponseEntity<String> deleteWishProduct(@LoginUser User user, @RequestBody WishDeleteRequest wishDeleteRequest) {
        wishService.deleteWishList(user.getId(), wishDeleteRequest.productId());
>>>>>>> 74cf6b2bfb66a871340d58a87b34a502f4ead750
        return ResponseEntity.ok().body("위시리스트에서 상품이 삭제되었습니다.");
    }
}
