package gift.controller;

import gift.annotation.LoginMember;
import gift.dto.*;
import gift.service.WishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishes")
public class WishRestController {
    private final WishService wishService;

    public WishRestController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping
    public ResponseEntity<Void> addWish(@LoginMember MemberRequestDto memberRequest, @RequestBody WishRequestDto wishRequest){
        wishService.save(memberRequest, wishRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<WishResponseDto>> getMemberWishList(@LoginMember MemberRequestDto memberRequest){
        return ResponseEntity.status(HttpStatus.OK).body(wishService.getMemberWishListByMemberId(memberRequest.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeWish(@LoginMember MemberRequestDto memberRequest, @PathVariable Long wishId){
        wishService.deleteWishByMemberIdAndWishId(memberRequest.getId(), wishId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateQuantity(@LoginMember MemberRequestDto memberRequest, @PathVariable Long wishId, @RequestBody WishRequestDto wishRequest){
        wishService.updateQuantityByMemberIdAndWishId(memberRequest.getId(), wishId, wishRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
