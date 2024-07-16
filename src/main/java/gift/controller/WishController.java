package gift.controller;

import gift.LoginMember;
import gift.classes.RequestState.RequestStateDTO;
import gift.classes.RequestState.RequestStatus;
import gift.classes.RequestState.WishListRequestStateDTO;
import gift.dto.MemberDto;
import gift.dto.RequestWishDto;
import gift.dto.WishDto;
import gift.services.MemberService;
import gift.services.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
public class WishController {

    public final WishService wishService;
    public final MemberService memberService;

    @Autowired
    public WishController(WishService wishService, MemberService memberService) {
        this.wishService = wishService;
        this.memberService = memberService;
    }

    //    Wish 추가
    @PostMapping
    public ResponseEntity<RequestStateDTO> addWish(@LoginMember MemberDto memberDto,
        @RequestBody RequestWishDto requestWishDto) {

        wishService.addWish(memberDto, requestWishDto.getProductId());
        return ResponseEntity.ok().body(new RequestStateDTO(
            RequestStatus.success,
            null
        ));
    }

    //    Wishlist 조회
    @GetMapping
    public ResponseEntity<WishListRequestStateDTO> getWishlistById(@LoginMember MemberDto memberDto,
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<WishDto> wishes = wishService.getWishListById(memberDto.getId(), page, size);
        return ResponseEntity.ok().body(new WishListRequestStateDTO(
            RequestStatus.success,
            null,
            wishes
        ));
    }

    //    Wish 삭제
    @DeleteMapping
    public void deleteWish(@LoginMember MemberDto memberDto,
        @RequestBody RequestWishDto requestWishDto) {
        wishService.deleteWish(memberDto.getId(), requestWishDto.getProductId());

    }
}
