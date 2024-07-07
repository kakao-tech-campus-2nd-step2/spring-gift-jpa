package gift.Login.controller;

import gift.Login.auth.LoginMember;
import gift.Login.model.Member;
import gift.Login.model.Product;
import gift.Login.model.Wishlist;
import gift.Login.service.WishlistService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishes")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    // 위시리스트에 상품 추가
    @PostMapping
    public void addProductToWishlist(@LoginMember Member member, @RequestBody Product product) {
        wishlistService.addProductToWishlist(member.getId(), product);
    }

    // 위시리시트 조회
    @GetMapping
    public Wishlist getWishlist(@LoginMember Member member) {
        return wishlistService.getWishlistByMemberId(member.getId());
    }

    // 위시리스트에 상품 수정
    @PutMapping("/{productId}")
    public void updateProductInWishlist(@LoginMember Member member, @PathVariable Long productId, @RequestBody Product product) {
        wishlistService.updateProductInWishlist(member.getId(), productId, product);
    }

    // 위시리스트에서 상품 삭제
    @DeleteMapping("/{productId}")
    public void removeProductFromWishlist(@LoginMember Member member, @PathVariable Long productId) {
        wishlistService.removeProductFromWishlist(member.getId(), productId);
    }
}
