package gift.controller.wish;

import gift.controller.wish.dto.WishRequest;
import gift.controller.wish.dto.WishResponse;
import gift.global.auth.Authenticate;
import gift.global.auth.Authorization;
import gift.global.auth.LoginInfo;
import gift.global.dto.PageResponse;
import gift.model.member.Role;
import gift.service.WishService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/wishes")
public class WishController {

    private final WishService wishService;

    @Autowired
    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @Authorization(role = Role.USER)
    @PostMapping("")
    public ResponseEntity<String> addWish(
        @Authenticate LoginInfo loginInfo,
        @Valid @RequestBody WishRequest.Register request
    ) {
        wishService.addWish(loginInfo.memberId(), request);
        return ResponseEntity.ok().body("Wish insert successfully.");
    }

    @Authorization(role = Role.USER)
    @DeleteMapping("/{wishId}")
    public ResponseEntity<String> deleteWish(
        @Authenticate LoginInfo loginInfo,
        @PathVariable("wishId") Long wishId
    ) {
        wishService.deleteWish(loginInfo.memberId(), wishId);
        return ResponseEntity.ok().body("Wish removed successfully.");
    }

    @Authorization(role = Role.USER)
    @GetMapping("")
    public ResponseEntity<PageResponse<WishResponse.Info>> getWishes(
        @Authenticate LoginInfo loginInfo,
        @PageableDefault(size = 5) Pageable pageable
    ) {
        var response = wishService.getWishesPaging(loginInfo.memberId(), pageable);
        return ResponseEntity.ok().body(response);
    }

    @Authorization(role = Role.USER)
    @PatchMapping("")
    public ResponseEntity<String> updateWish(
        @Authenticate LoginInfo loginInfo,
        @Valid @RequestBody WishRequest.Update request
    ) {
        wishService.updateWish(loginInfo.memberId(), request);
        return ResponseEntity.ok().body("Wish updated successfully.");
    }
}
