package gift.member.presentation.restcontroller;

import gift.global.authentication.annotation.MemberId;
import gift.member.business.dto.JwtToken;
import gift.member.business.service.MemberService;
import gift.member.business.service.WishlistService;
import gift.member.presentation.dto.RequestMemberDto;
import gift.member.presentation.dto.RequestWishlistDto;
import gift.member.presentation.dto.ResponseWishListDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final WishlistService wishlistService;

    public MemberController(MemberService memberService, WishlistService wishlistService) {
        this.memberService = memberService;
        this.wishlistService = wishlistService;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtToken> registerMember(
        @RequestBody @Valid RequestMemberDto requestMemberDto) {
        var jwtToken = memberService.registerMember(requestMemberDto.toMemberRegisterDto());
        return ResponseEntity.status(HttpStatus.CREATED).body(jwtToken);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> loginMember(
        @RequestBody @Valid RequestMemberDto requestMemberDto) {
        var jwtToken = memberService.loginMember(requestMemberDto.toMemberLoginDto());
        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/reissue")
    public ResponseEntity<String> reissueRefreshToken(
        @RequestHeader("Authorization") String refreshToken) {
        var accessToken = memberService.reissueAccessToken(refreshToken);
        return ResponseEntity.ok(accessToken);
    }

    @GetMapping("/wishlists")
    public ResponseEntity<List<ResponseWishListDto>> getWishLists(@MemberId Long memberId) {
        var wishListDtoList = wishlistService.getWishLists(memberId);
        var responseWishListDtoList = wishListDtoList.stream().map(ResponseWishListDto::from)
            .toList();

        return ResponseEntity.ok(responseWishListDtoList);
    }

    @PostMapping("/wishlists/products/{productId}")
    public ResponseEntity<Long> addWishList(@MemberId Long memberId, @PathVariable("productId") Long productId) {
        var wishListId = wishlistService.addWishList(memberId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(wishListId);
    }

    @PutMapping("/wishlists/products/{productId}")
    public ResponseEntity<Long> updateWishList(@MemberId Long memberId,
        @PathVariable("productId") Long productId,
        @RequestBody @Valid RequestWishlistDto requestWishlistDto) {
        var wishListId = wishlistService.updateWishList(memberId, requestWishlistDto.toWishListUpdateDto(productId));
        return ResponseEntity.ok(wishListId);
    }

    @DeleteMapping("/wishlists/products/{productId}")
    public ResponseEntity<Void> deleteWishList(@MemberId Long memberId, @PathVariable("productId") Long productId) {
        wishlistService.deleteWishList(memberId, productId);
        return ResponseEntity.ok().build();
    }


}
