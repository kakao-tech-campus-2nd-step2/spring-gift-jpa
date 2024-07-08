package gift.wishlist.controller;

import gift.member.model.Member;
import gift.member.service.MemberService;
import gift.wishlist.dto.WishResponse;
import gift.wishlist.dto.WishRequest;
import gift.wishlist.service.WishService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
        WishResponse wishResponse = wishService.addWish(member.getId(), request);
        return ResponseEntity.ok(wishResponse);
    }

    @GetMapping
    public ResponseEntity<List<WishResponse>> getWishes(Member member) {
        List<WishResponse> wishes = wishService.getWishes(member.getId());
        return ResponseEntity.ok(wishes);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteWishByProductName(Member member, @RequestBody WishRequest request) {
        wishService.deleteWishByProductName(member.getId(), request.getProductName());
        return ResponseEntity.noContent().build();
    }

}
