package gift.controller;

import gift.authentication.LoginMember;
import gift.authentication.UserDetails;
import gift.dto.*;
import gift.service.WishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gift.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;
    private final WishService wishService;

    public MemberController(MemberService memberService, WishService wishService) {
        this.memberService = memberService;
        this.wishService = wishService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberRegisterResponseDto> signUp(@RequestBody MemberRegisterRequestDto request) {
        return ResponseEntity.ok(memberService.signUpMember(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody MemberRequestDto request) {
        String token = memberService.loginMember(request);
        return ResponseEntity.ok(new TokenResponseDto(token));
    }

    @PostMapping("/wishes")
    public ResponseEntity<ApiResponse> addNewWish(
            @LoginMember UserDetails userDetails,
            @RequestBody WishAddRequestDto request) {
        wishService.addWish(userDetails.id(), request);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "위시 리스트 등록에 성공하였습니다."));
    }

    @GetMapping("/wishes")
    public ResponseEntity<List<WishResponseDto>> getMemberWishList(@LoginMember UserDetails userDetails) {
        return ResponseEntity.ok(wishService.getAllWishes(userDetails.id()));
    }

    @DeleteMapping("/wishes/{id}")
    public ResponseEntity<ApiResponse> deleteWish(
            @LoginMember UserDetails userDetails,
            @PathVariable("id") Long productId) {
        wishService.deleteWish(userDetails.id(), productId);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.NO_CONTENT, "성공적으로 삭제되었습니다."));
    }
}
