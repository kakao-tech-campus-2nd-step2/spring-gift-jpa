package gift.controller;

import gift.annotation.LoginUserId;
import gift.dto.wish.WishResponse;
import gift.dto.wish.WishRequest;
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
    public ResponseEntity<List<WishResponse>> addWish(
        @LoginUserId Long userId, @RequestBody @Valid WishRequest wishRequest
    ) {
        return ResponseEntity.ok(wishService.addWish(userId, wishRequest));
    }

    @PatchMapping
    public ResponseEntity<List<WishResponse>> updateWishes(
        @LoginUserId Long userId, @RequestBody List<WishRequest> wishRequests
    ) {
        return ResponseEntity.ok(wishService.updateWishes(userId, wishRequests));
    }

    @DeleteMapping
    public ResponseEntity<List<WishResponse>> deleteWishes(
        @LoginUserId Long userId, @RequestBody List<WishRequest> wishRequests
    ) {
        return ResponseEntity.ok(wishService.deleteWishes(userId, wishRequests));
    }
}
