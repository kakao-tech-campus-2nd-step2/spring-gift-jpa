package gift.wishlist.controller;

import gift.member.model.Member;
import gift.wishlist.dto.WishResponse;
import gift.wishlist.dto.WishRequest;
import gift.wishlist.service.WishService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishes")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping
    public ResponseEntity<WishResponse> addWish(Member member, @RequestBody WishRequest request) {
        WishResponse wishResponse = wishService.addWish(member, request);
        return ResponseEntity.ok(wishResponse);
    }

    @GetMapping
    public ResponseEntity<List<WishResponse>> getWishes(Member member) {
        List<WishResponse> wishes = wishService.getWishes(member);
        return ResponseEntity.ok(wishes);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<WishResponse>> getWishesPaged(Member member,
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<WishResponse> wishesPage = wishService.getWishes(member, pageable);
        return ResponseEntity.ok(wishesPage);
    }

    @DeleteMapping("/prooductId/{productId}")
    public ResponseEntity<Void> deleteWishByProductName(Member member,
        @PathVariable Long productId) {
        wishService.deleteWishByProductId(member, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishById(@PathVariable Long id) {
        wishService.deleteWishById(id);
        return ResponseEntity.noContent().build();
    }

}
