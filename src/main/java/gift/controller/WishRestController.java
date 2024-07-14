package gift.controller;

import gift.annotation.LoginMember;
import gift.dto.*;
import gift.service.WishService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<List<WishResponseDto>> getMemberWishes(@LoginMember MemberRequestDto memberRequest, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK)
                .body(wishService.getPagedMemberWishesByMemberId(memberRequest.getId(),pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeWish(@LoginMember MemberRequestDto memberRequest, @PathVariable Long id){
        wishService.deleteWishByMemberIdAndId(memberRequest.getId(), id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateQuantity(@LoginMember MemberRequestDto memberRequest, @PathVariable Long id, @RequestBody WishRequestDto wishRequest){
        wishService.updateQuantityByMemberIdAndId(memberRequest.getId(), id, wishRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
