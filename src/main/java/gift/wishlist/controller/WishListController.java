package gift.wishlist.controller;

import gift.auth.LoginMember;
import gift.member.dto.MemberResDto;
import gift.wishlist.dto.WishListReqDto;
import gift.wishlist.dto.WishListResDto;
import gift.wishlist.service.WishListService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wish-list")
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping
    public ResponseEntity<List<WishListResDto>> getWishLists(@LoginMember MemberResDto member) {
        List<WishListResDto> wishList = wishListService.getWishListsByMemberId(member.id());
        return ResponseEntity.ok(wishList);
    }

    @PostMapping
    public ResponseEntity<String> addWishList(@LoginMember MemberResDto member, @RequestBody WishListReqDto wishListReqDto) {
        wishListService.addWishList(member.id(), wishListReqDto);
        return ResponseEntity.ok("상품을 장바구니에 담았습니다.");
    }

    @PutMapping("/{wish-list-id}")
    public ResponseEntity<String> updateWishList(
            @LoginMember MemberResDto member,
            @PathVariable("wish-list-id") Long wishListId,
            @RequestBody WishListReqDto wishListReqDto
    ) {
        wishListService.updateWishListById(member.id(), wishListId, wishListReqDto);
        return ResponseEntity.ok("담긴 상품의 수량을 변경했습니다.");
    }

    @DeleteMapping("/{wish-list-id}")
    public ResponseEntity<String> deleteWishList(
            @LoginMember MemberResDto member,
            @PathVariable("wish-list-id") Long wishListId
    ) {
        wishListService.deleteWishListById(member.id(), wishListId);
        return ResponseEntity.ok("상품을 장바구니에서 삭제했습니다.");
    }
}
