package gift.controller;

import gift.annotation.LoginUserId;
import gift.config.PageConfig;
import gift.dto.wish.AddWishRequest;
import gift.dto.wish.UpdateWishRequest;
import gift.dto.wish.WishResponse;
import gift.service.WishService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<Page<WishResponse>> getWishes(
        @LoginUserId Long userId,
        @PageableDefault(
            size = PageConfig.PAGE_PER_COUNT,
            sort = PageConfig.SORT_STANDARD,
            direction = Direction.DESC
        ) Pageable pageable
    ) {
        return ResponseEntity.ok(wishService.getWishes(userId, pageable));
    }

    @PostMapping
    public ResponseEntity<Long> addWish(
        @LoginUserId Long userId, @RequestBody @Valid AddWishRequest request
    ) {
        return ResponseEntity.ok(wishService.addWish(userId, request));
    }

    @PatchMapping
    public ResponseEntity<Void> updateWishes(
        @LoginUserId Long userId, @RequestBody List<UpdateWishRequest> requests
    ) {
        wishService.updateWishes(requests);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping({"id"})
    public ResponseEntity<Void> deleteWishes(
        @LoginUserId Long userId, @RequestParam Long id
    ) {
        wishService.deleteWish(id);
        return ResponseEntity.ok().build();
    }
}
