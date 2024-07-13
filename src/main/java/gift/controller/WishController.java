package gift.controller;

import gift.dto.wish.WishCreateRequest;
import gift.dto.wish.WishResponse;
import gift.service.WishService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
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
    public ResponseEntity<List<WishResponse>> getWishlist(
        @RequestAttribute("memberId") Long memberId) {
        List<WishResponse> wishlist = wishService.getWishlistByMemberId(memberId);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping
    public ResponseEntity<WishResponse> addWish(
        @Valid @RequestBody WishCreateRequest wishCreateRequest,
        @RequestAttribute("memberId") Long memberId) {
        WishResponse createdWish = wishService.addWish(wishCreateRequest, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWish);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWish(@PathVariable Long id) {
        wishService.deleteWish(id);
        return ResponseEntity.noContent().build();
    }
}
