package gift.domain.controller;

import gift.domain.entity.User;
import gift.domain.annotation.ValidUser;
import gift.domain.dto.WishDeleteRequestDto;
import gift.domain.dto.WishRequestDto;
import gift.domain.service.WishService;
import gift.global.response.SuccessResponse;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getWishlist(@ValidUser User user) {
        return SuccessResponse.ok(wishService.getWishlist(user), "wishlist");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addWishlist(@ValidUser User user, @Valid @RequestBody WishRequestDto wishRequestDto) {
        return SuccessResponse.ok(wishService.addWishlist(user, wishRequestDto), "result");
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateWishlist(@ValidUser User user, @Valid @RequestBody WishRequestDto wishRequestDto) {
        return SuccessResponse.ok(wishService.updateWishlist(user, wishRequestDto), "result");
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteWishlist(@ValidUser User user, @RequestBody WishDeleteRequestDto wishDeleteRequestDto) {
        wishService.deleteWishlist(user, wishDeleteRequestDto);
        return SuccessResponse.of(HttpStatus.NO_CONTENT);
    }
}
