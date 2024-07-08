package gift.controller;

import gift.annotation.LoginMember;
import gift.dto.MemberResponseDto;
import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
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
    public ResponseEntity<Void> addWish(@LoginMember MemberResponseDto memberResponseDto, @RequestBody WishRequestDto wishRequest){
        wishService.save(memberResponseDto, wishRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<WishResponseDto>> getWishList(@LoginMember MemberResponseDto memberResponseDto){
        return ResponseEntity.status(HttpStatus.OK).body(wishService.findByUserEmail(memberResponseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeWish(@LoginMember MemberResponseDto memberResponseDto, @PathVariable Long id){
        wishService.delete(memberResponseDto, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateWishQuantity(@LoginMember MemberResponseDto memberResponseDto, @PathVariable Long id, @RequestBody WishRequestDto wishRequest){
        wishService.updateQuantity(memberResponseDto, id, wishRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
