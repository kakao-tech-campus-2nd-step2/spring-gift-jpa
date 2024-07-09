package gift.wishlist.controller;

import gift.user.model.dto.User;
import gift.user.resolver.LoginUser;
import gift.user.service.UserService;
import gift.wishlist.model.dto.AddWishRequest;
import gift.wishlist.model.dto.WishListResponse;
import gift.wishlist.service.WishListService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    private final UserService userService;

    public WishListController(WishListService wishListService, UserService userService) {
        this.wishListService = wishListService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<WishListResponse>> getWishListForUser(@LoginUser User loginUser) {
        final List<WishListResponse> responses = wishListService.getWishList(loginUser.getId());
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/admin/{userId}")
    public ResponseEntity<List<WishListResponse>> getWishListForAdmin(@LoginUser User loginUser,
                                                                      @PathVariable("userId") Long userId) {
        userService.verifyAdminAccess(loginUser);
        final List<WishListResponse> responses = wishListService.getWishList(userId);
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping
    public ResponseEntity<String> addWish(@LoginUser User loginUser, @RequestBody AddWishRequest addWishRequest) {
        wishListService.addWish(loginUser.getId(), addWishRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }

    @PostMapping("/admin/{userId}")
    public ResponseEntity<String> addWishForAdmin(@LoginUser User loginUser, @PathVariable("userId") Long userId,
                                                  @RequestBody AddWishRequest addWishRequest) {
        userService.verifyAdminAccess(loginUser);
        wishListService.addWish(userId, addWishRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }

    @PatchMapping
    public ResponseEntity<String> updateWishQuantity(@LoginUser User loginUser, @RequestParam Long wishId,
                                                     @RequestParam int quantity) {
        wishListService.updateWishQuantity(loginUser.getId(), wishId, quantity);
        return ResponseEntity.ok().body("ok");
    }

    @PatchMapping("/admin/{userId}")
    public ResponseEntity<String> updateWishQuantityForAdmin(@LoginUser User loginUser,
                                                             @PathVariable("userId") Long userId,
                                                             @RequestParam Long wishId,
                                                             @RequestParam int quantity) {
        userService.verifyAdminAccess(loginUser);
        wishListService.updateWishQuantity(userId, wishId, quantity);
        return ResponseEntity.ok().body("ok");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteWish(@LoginUser User loginUser, @RequestParam Long wishId) {
        wishListService.deleteWish(loginUser.getId(), wishId);
        return ResponseEntity.ok().body("ok");
    }

    @DeleteMapping("/admin/{userId}")
    public ResponseEntity<String> deleteWishForAdmin(@LoginUser User loginUser, @PathVariable("userId") Long userId,
                                                     @RequestParam Long wishId) {
        userService.verifyAdminAccess(loginUser);
        wishListService.deleteWish(userId, wishId);
        return ResponseEntity.ok().body("ok");
    }
}
