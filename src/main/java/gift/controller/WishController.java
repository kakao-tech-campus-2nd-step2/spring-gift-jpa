package gift.controller;

import gift.LoginMember;
import gift.dto.MemberDto;
import gift.dto.RequestWishDto;
import gift.dto.WishDto;
import gift.services.MemberService;
import gift.services.WishService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
public class WishController {
    public final WishService wishService;
    public final MemberService memberService;

    public WishController(WishService wishService, MemberService memberService) {
        this.wishService = wishService;
        this.memberService = memberService;
    }

//    Wish 추가
    @PostMapping
    public String addWish(@LoginMember MemberDto memberDto, @RequestBody RequestWishDto requestWishDto) {
        wishService.addWish(memberDto.getMemberId(), requestWishDto.getProductId());
        return "done";
    }

//    Wishlist 조회
    @GetMapping
    public List<WishDto> getWishlistById(@LoginMember MemberDto memberDto) {
        return wishService.getWishListById(memberDto.getMemberId());
    }

//    Wish 삭제
    @DeleteMapping
    public void deleteWish(@LoginMember MemberDto memberDto, @RequestBody RequestWishDto requestWishDto){
        wishService.deleteWish(memberDto.getMemberId(), requestWishDto.getProductId());

    }
}
