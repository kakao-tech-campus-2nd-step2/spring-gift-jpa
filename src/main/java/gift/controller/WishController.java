package gift.controller;

import gift.dto.wish.WishCreateRequest;
import gift.dto.wish.WishRequest;
import gift.dto.wish.WishResponse;
import gift.service.WishService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<List<WishResponse>> getWishlist(HttpServletRequest request) {
        Long memberId = wishService.getMemberIdFromRequest(request);
        List<WishResponse> wishlist = wishService.getWishlistByMemberId(memberId);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping
    public ResponseEntity<WishResponse> addWish(@Valid @RequestBody WishCreateRequest wishRequestDTO, HttpServletRequest request) {
        Long memberId = wishService.getMemberIdFromRequest(request);
        WishRequest wishWithMemberId = new WishRequest(memberId, wishRequestDTO.productId());
        WishResponse createdWish = wishService.addWish(wishWithMemberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWish);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWish(@PathVariable Long id) {
        wishService.deleteWish(id);
        return ResponseEntity.noContent().build();
    }
}
