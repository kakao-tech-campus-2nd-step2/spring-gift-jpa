package gift.controller;

import gift.common.annotation.LoginUser;
import gift.common.dto.PageResponse;
import gift.model.user.LoginUserRequest;
import gift.model.wish.WishRequest;
import gift.model.wish.WishResponse;
import gift.model.wish.WishUpdateRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wish")
public class WishListController {

    private final WishService wishService;

    public WishListController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping("")
    public ResponseEntity<PageResponse<WishResponse>> getAllWishList(
        @LoginUser LoginUserRequest user,
        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
        @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        PageResponse responses = wishService.findAllWish(user.id(), page, size);
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("")
    public ResponseEntity<String> addWishProduct(@LoginUser LoginUserRequest user,
        @Valid @RequestBody WishRequest wishRequest) {
        wishService.addWistList(user.id(), wishRequest);
        return ResponseEntity.ok().body("위시리스트에 상품이 추가되었습니다.");
    }

    @PatchMapping("/{wishId}")
    public ResponseEntity<String> updateWishProduct(@PathVariable("wishId") Long wishId,
        @LoginUser LoginUserRequest user,
        @Valid @RequestBody WishUpdateRequest wishRequest) {
        wishService.updateWishList(user.id(), wishId, wishRequest);
        return ResponseEntity.ok().body("위시리스트에 상품이 수정되었습니다.");
    }

    @DeleteMapping("/{wishId}")
    public ResponseEntity<String> deleteWishProduct(@PathVariable("wishId") Long wishId,
        @LoginUser LoginUserRequest user) {
        wishService.deleteWishList(user.id(), wishId);
        return ResponseEntity.ok().body("위시리스트에서 상품이 삭제되었습니다.");
    }
}
