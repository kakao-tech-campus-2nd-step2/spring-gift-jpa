package gift.controller;

import gift.domain.Member;
import gift.domain.Wish;
import gift.security.LoginMember;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gift.service.WishService;

import java.util.List;

@RestController
@RequestMapping("/api/wishes")
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<List<Wish>> getWishList(@LoginMember Member member) {
        List<Wish> wishList = wishService.getWishList(member.getId());
        return ResponseEntity.ok(wishList);
    }

    @PostMapping
    public ResponseEntity<Void> addToWishList(@LoginMember Member member, @RequestParam Long productId) {
        wishService.addToWishList(member.getId(), productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFromWishList(@LoginMember Member member, @RequestParam Long productId) {
        wishService.removeFromWishList(member.getId(), productId);
        return ResponseEntity.noContent().build();
    }
}

