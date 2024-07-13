package gift.controller;

import gift.annotation.LoginUser;
import gift.dto.WishlistRequestDto;
import gift.dto.WishlistResponseDto;
import gift.entity.Wish;
import gift.service.MemberService;
import gift.service.WishService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/wishlist")
public class WishlistController {
    private final WishService wishService;
    private final MemberService memberService;

    public WishlistController(WishService wishService, MemberService memberService) {
        this.wishService = wishService;
        this.memberService = memberService;
    }

//    @GetMapping
//    public ResponseEntity<List<WishlistResponseDto>> getAllWishlists(@LoginUser String email) {
//        Long memberId = memberService.getMemberId(email);
//        return new ResponseEntity<>(wishService.getWishListByMemberId(memberId), HttpStatus.OK);
//    }
    @GetMapping
    public ResponseEntity<List<WishlistResponseDto>> getAllWishlists(
        @LoginUser String email,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        Long memberId = memberService.getMemberId(email);
        Pageable pageable = PageRequest.of(page, size);
        Page<Wish> wishPage = wishService.findByMemberId(memberId, pageable);
        List<WishlistResponseDto> wishList = wishPage.stream()
            .map(wish -> new WishlistResponseDto(
                wish.getMember().getId(),
                wish.getProduct().getId()
            ))
            .collect(Collectors.toList());
        return new ResponseEntity<>(wishList, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<WishlistResponseDto> addWishlist (@LoginUser String email, @RequestBody WishlistRequestDto requestDto) {
        Long memberId = memberService.getMemberId(email);
        WishlistResponseDto wishlistResponseDto = new WishlistResponseDto(memberId, requestDto.getProductId());
        if(wishService.addWishlist(memberId, requestDto.getProductId())) {
            return new ResponseEntity<>(wishlistResponseDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(wishlistResponseDto, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Long> deleteWishlist(@PathVariable Long productId, @LoginUser String email) {
        Long memberId = memberService.getMemberId(email);
        if(wishService.deleteWishlist(memberId, productId)){
            return new ResponseEntity<>(memberId, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
