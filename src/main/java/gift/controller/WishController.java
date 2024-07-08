package gift.controller;

import gift.annotation.LoginUserId;
import gift.dto.wish.WishResponse;
import gift.dto.wish.WishRequest;
import gift.service.WishService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<>(wishService.getWishes(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<WishResponse>> addWish(
        @LoginUserId Long userId, @RequestBody @Valid WishRequest wishRequest
    ) {
        return new ResponseEntity<>(wishService.addWish(userId, wishRequest), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<List<WishResponse>> updateWishes(
        @LoginUserId Long userId, @RequestBody List<WishRequest> wishRequests
    ) {
        return new ResponseEntity<>(wishService.updateWishes(userId, wishRequests), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<List<WishResponse>> deleteWishes(
        @LoginUserId Long userId, @RequestBody List<WishRequest> wishRequests
    ) {
        return new ResponseEntity<>(wishService.deleteWishes(userId, wishRequests), HttpStatus.OK);
    }
}
