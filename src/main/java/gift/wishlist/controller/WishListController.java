package gift.wishlist.controller;

import gift.user.model.dto.AppUser;
import gift.user.resolver.LoginUser;
import gift.user.service.UserService;
import gift.wishlist.model.dto.AddWishRequest;
import gift.wishlist.model.dto.WishListResponse;
import gift.wishlist.service.WishListService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<List<WishListResponse>> getWishListForUser(@LoginUser AppUser loginAppUser) {
        final List<WishListResponse> responses = wishListService.getWishList(loginAppUser.getId());
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/admin/{userId}")
    public ResponseEntity<List<WishListResponse>> getWishListForAdmin(@LoginUser AppUser loginAppUser,
                                                                      @PathVariable("userId") Long userId) {
        userService.verifyAdminAccess(loginAppUser);
        final List<WishListResponse> responses = wishListService.getWishList(userId);
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<WishListResponse>> getWishListForUser(@LoginUser AppUser loginAppUser,
                                                                     @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
                                                                     Pageable pageable) {
        Page<WishListResponse> responses = wishListService.getWishList(loginAppUser.getId(), pageable);
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping
    public ResponseEntity<String> addWish(@LoginUser AppUser loginAppUser, @RequestBody AddWishRequest addWishRequest) {
        wishListService.addWish(loginAppUser.getId(), addWishRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }

    @PostMapping("/admin/{userId}")
    public ResponseEntity<String> addWishForAdmin(@LoginUser AppUser loginAppUser, @PathVariable("userId") Long userId,
                                                  @RequestBody AddWishRequest addWishRequest) {
        userService.verifyAdminAccess(loginAppUser);
        wishListService.addWish(userId, addWishRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }

    @PatchMapping
    public ResponseEntity<String> updateWishQuantity(@LoginUser AppUser loginAppUser, @RequestParam Long wishId,
                                                     @RequestParam int quantity) {
        wishListService.updateWishQuantity(loginAppUser.getId(), wishId, quantity);
        return ResponseEntity.ok().body("ok");
    }

    @PatchMapping("/admin/{userId}")
    public ResponseEntity<String> updateWishQuantityForAdmin(@LoginUser AppUser loginAppUser,
                                                             @PathVariable("userId") Long userId,
                                                             @RequestParam Long wishId,
                                                             @RequestParam int quantity) {
        userService.verifyAdminAccess(loginAppUser);
        wishListService.updateWishQuantity(userId, wishId, quantity);
        return ResponseEntity.ok().body("ok");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteWish(@LoginUser AppUser loginAppUser, @RequestParam Long wishId) {
        wishListService.deleteWish(loginAppUser.getId(), wishId);
        return ResponseEntity.ok().body("ok");
    }

    @DeleteMapping("/admin/{userId}")
    public ResponseEntity<String> deleteWishForAdmin(@LoginUser AppUser loginAppUser,
                                                     @PathVariable("userId") Long userId,
                                                     @RequestParam Long wishId) {
        userService.verifyAdminAccess(loginAppUser);
        wishListService.deleteWish(userId, wishId);
        return ResponseEntity.ok().body("ok");
    }
}
