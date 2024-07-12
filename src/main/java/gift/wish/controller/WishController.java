package gift.wish.controller;

import gift.common.auth.LoginMember;
import gift.member.model.Member;
import gift.product.model.Product;
import gift.wish.model.Wish;
import gift.wish.model.WishDTO;
import gift.wish.model.WishRequest;
import gift.wish.service.WishService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wish")
public class WishController {

    private final WishService wishlistService;

    public WishController(WishService wishlistService) {
        this.wishlistService = wishlistService;
    }

    // 1. 사용자 위시리스트에 상품 추가
    @PostMapping
    public void createWish(@LoginMember Member member, @RequestBody WishRequest wishRequest) {
        wishlistService.createWish(member, wishRequest.getProductId());
    }

    // 2. 사용자 위시리스트 상품 전체 조회
    @GetMapping
    public List<WishDTO> getWishlist(@LoginMember Member member) {
        return wishlistService.getWishlistByMemberId(member);
    }

    // 3. 사용자의 위시리스트 삭제
    @DeleteMapping("/{wishId}")
    public void deleteWish(@LoginMember Member member, @PathVariable Long wishId) {
        wishlistService.deleteWish(wishId);
    }
}
