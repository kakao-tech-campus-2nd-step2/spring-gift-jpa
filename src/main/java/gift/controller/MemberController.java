package gift.controller;

import gift.authentication.LoginMember;
import gift.authentication.UserDetails;
import gift.dto.*;
import gift.service.MemberService;
import gift.service.WishService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse> addNewWish(@LoginMember UserDetails userDetails,
                                                  @RequestBody WishAddRequestDto request) {
        wishService.addWish(userDetails.id(), request);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "위시 리스트 등록에 성공하였습니다."));
    }

    @GetMapping("/wishes")
    public ResponseEntity<List<WishResponseDto>> getMemberWishList(
            @LoginMember UserDetails userDetails,
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNum,
            @RequestParam(required = false, defaultValue = "10", value = "size") int size,
            @RequestParam(required = false, defaultValue = "id", value = "criteria") String criteria) {
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(Sort.Direction.ASC, criteria));
        return ResponseEntity.ok(wishService.getAllWishes(userDetails.id(), pageable));
    }

    @PutMapping("/wishes/{id}")
    public ResponseEntity<ApiResponse> updateWish(
            @LoginMember UserDetails userDetails,
            @PathVariable("id") Long id,
            @RequestBody WishUpdateRequestDto requestDto) {
        wishService.updateWish(userDetails.id(), id, requestDto.quantity());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "수량이 성공적으로 수정되었습니다."));
    }

    @DeleteMapping("/wishes/{id}")
    public ResponseEntity<ApiResponse> deleteWish(
            @LoginMember UserDetails userDetails,
            @PathVariable("id") Long productId) {
        wishService.deleteWish(userDetails.id(), productId);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.NO_CONTENT, "성공적으로 삭제되었습니다."));
    }
}
