package gift.controller;

import gift.annotation.LoginMember;
import gift.domain.Wishlist;
import gift.dto.WishlistRequestDto;
import gift.service.MemberService;
import gift.service.WishlistService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    private WishlistService wishlistService;
    private MemberService memberService;

    public WishlistController(WishlistService wishlistService, MemberService memberService) {
        this.wishlistService = wishlistService;
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<Wishlist>> getAllWishlists(@LoginMember Long memberId) {
        return new ResponseEntity<>(wishlistService.getWishlistById(memberId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addWishlist(@LoginMember Long memberId, @RequestBody WishlistRequestDto wishlistRequestDto) {
        wishlistService.addWishItem(new Wishlist(memberId, wishlistRequestDto.getProductId(), wishlistRequestDto.getQuantity()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteWishlist(@LoginMember String email, @PathVariable Long productId) {
        Long memberId = memberService.getMemberIdByEmail(email);
        wishlistService.deleteWishItem(memberId, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
