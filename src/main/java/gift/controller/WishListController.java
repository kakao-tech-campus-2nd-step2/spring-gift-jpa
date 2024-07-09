package gift.controller;

import gift.common.annotation.LoginUser;
import gift.model.product.ProductListResponse;
import gift.model.user.User;
import gift.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wish")
public class WishListController {

    private final UserService userService;

    public WishListController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/products")
    public ResponseEntity<ProductListResponse> getAllWishList(@LoginUser User user) {
        ProductListResponse responses = userService.findWishList(user.getId());
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<String> addWishProduct(@LoginUser User user, @PathVariable("productId") Long productId, int count) {
        userService.addWistList(user.getId(), productId, count);
        return ResponseEntity.ok().body("위시리스트에 상품이 추가되었습니다.");
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<String> deleteWishProduct(@LoginUser User user, @PathVariable("productId") Long productId, int count) {
        userService.deleteWishList(user.getId(), productId, count);
        return ResponseEntity.ok().body("위시리스트에서 상품이 삭제되었습니다.");
    }
}
