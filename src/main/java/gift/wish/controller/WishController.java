package gift.wish.controller;

import gift.common.auth.LoginMember;
import gift.member.model.Member;
import gift.product.model.Product;
import gift.wish.model.Wish;
import gift.wish.service.WishService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishes")
public class WishController {

    private final WishService wishlistService;

    public WishController(WishService wishlistService) {
        this.wishlistService = wishlistService;
    }

    // 1. 사용자 위시리스트에 상품 추가
    @PostMapping
    public void addProductToWishlist(@LoginMember Member member, @RequestBody Product product) {
        wishlistService.addProductToWishlist(member, product);
    }

    // 2. 사용자 위시리스트 상품 조회
    @GetMapping
    public List<Wish> getWishlist(@LoginMember Member member) {
        return wishlistService.getWishlistByMemberId(member);
    }

    // 3. 사용자 위시리스트에 있는 상품 수정
    @PutMapping("/{productId}")
    public void updateProductInWishlist(@LoginMember Member member, @PathVariable Long productId, @RequestBody Product product) {
        wishlistService.updateProductInWishlist(member.getId(), productId, product);
    }

    // 4. 사용자 위시리스트에 있는 상품 삭제
    @DeleteMapping("/{productId}")
    public void removeProductFromWishlist(@LoginMember Member member, @PathVariable Long productId) {
        wishlistService.removeProductFromWishlist(member.getId(), productId);
    }
}
