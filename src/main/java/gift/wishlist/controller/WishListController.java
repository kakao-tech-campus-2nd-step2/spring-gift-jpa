package gift.wishlist.controller;

import gift.product.model.Product;
import gift.wishlist.service.WishListService;
import gift.wishlist.model.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    private WishListService wishListService;

    // id로 위시리스트 연결하려면
    @GetMapping("/{userId}")
    public WishList getWishList(@PathVariable Long userId) {
        return wishListService.findByUserId(userId);
    }

    // 해당 위시리스트에 상품 추가 연결
    @PostMapping("/{userId}/add")
    public void addProductToWishList(@PathVariable Long userId, @RequestBody Product product) {
        wishListService.addProductToWishList(userId, product);
    }

    // 해당 위시리스트에 상품 삭제 연결
    @DeleteMapping("/{userId}/remove/{productId}")
    public void removeProductFromWishList(@PathVariable Long userId, @PathVariable Long productId) {
        wishListService.removeProductFromWishList(userId, productId);
    }
}