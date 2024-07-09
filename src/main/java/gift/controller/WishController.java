package gift.controller;

import gift.annotation.LoginUserId;
import gift.dto.wish.AddWishRequest;
import gift.dto.wish.UpdateWishRequest;
import gift.dto.wish.WishResponse;
import gift.service.WishService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes")
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<List<WishResponse>> getWishes(@LoginUserId Long userId) {
        return ResponseEntity.ok(wishService.getWishes(userId));
    }

    @PostMapping
    public ResponseEntity<Long> addWish(
        @LoginUserId Long userId, @RequestBody @Valid AddWishRequest request
    ) {
        return ResponseEntity.ok(wishService.addWish(userId, request));
    }

    @PatchMapping
    public ResponseEntity updateWishes(
        @LoginUserId Long userId, @RequestBody List<UpdateWishRequest> requests
    ) {
        wishService.updateWishes(requests);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping
    public ResponseEntity deleteWishes(
        @LoginUserId Long userId, @RequestBody List<UpdateWishRequest> requests
    ) {
        wishService.deleteWishes(requests);
        return ResponseEntity.ok(null);
    }
}
