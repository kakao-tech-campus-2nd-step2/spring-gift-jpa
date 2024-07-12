package gift.domain.controller;

import gift.domain.annotation.ValidUser;
import gift.domain.controller.apiResponse.WishAddApiResponse;
import gift.domain.controller.apiResponse.WishListApiResponse;
import gift.domain.controller.apiResponse.WishUpdateApiResponse;
import gift.domain.dto.request.WishDeleteRequest;
import gift.domain.dto.request.WishRequest;
import gift.domain.entity.User;
import gift.domain.service.WishService;
import gift.global.apiResponse.BasicApiResponse;
import gift.global.apiResponse.SuccessApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<WishListApiResponse> getWishlist(@ValidUser User user) {
        return SuccessApiResponse.ok(new WishListApiResponse(HttpStatus.OK, wishService.getWishlist(user)));
    }

    @PostMapping
    public ResponseEntity<WishAddApiResponse> addWish(@ValidUser User user, @Valid @RequestBody WishRequest wishRequest) {
        return SuccessApiResponse.ok(new WishAddApiResponse(HttpStatus.OK, wishService.addWishlist(user,
            wishRequest)));
    }

    @PutMapping
    public ResponseEntity<WishUpdateApiResponse> updateWish(@ValidUser User user, @Valid @RequestBody WishRequest wishRequest) {
        return SuccessApiResponse.ok(new WishUpdateApiResponse(HttpStatus.OK, wishService.updateWishlist(user,
            wishRequest)));
    }

    @DeleteMapping
    public ResponseEntity<BasicApiResponse> deleteWish(@ValidUser User user, @RequestBody WishDeleteRequest wishDeleteRequest) {
        wishService.deleteWishlist(user, wishDeleteRequest);
        return SuccessApiResponse.of(HttpStatus.NO_CONTENT);
    }
}
