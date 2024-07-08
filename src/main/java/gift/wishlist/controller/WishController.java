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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishes")
public class WishController {

    private final WishService wishService;
    private final MemberService memberService;

    public WishController(WishService wishService, MemberService memberService) {
        this.wishService = wishService;
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<?> addWish(@RequestHeader("Authorization") String authorizationHeader, @RequestBody WishRequest request) {
        String token = authorizationHeader.substring(7); // "Bearer " 이후의 토큰만 추출
        Member member = memberService.getMemberFromToken(token);
        wishService.addWish(member.getId(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<WishResponse>> getWishes(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7); // "Bearer " 이후의 토큰만 추출
        Member member = memberService.getMemberFromToken(token);
        List<WishResponse> wishes = wishService.getWishes(member.getId());
        return ResponseEntity.ok(wishes);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteWishByProductName(@RequestHeader("Authorization") String authorizationHeader, @RequestBody WishRequest request) {
        String token = authorizationHeader.substring(7);
        Member member = memberService.getMemberFromToken(token);
        wishService.deleteWishByProductName(member.getId(), request.getProductName());
        return ResponseEntity.noContent().build();
    }

}
