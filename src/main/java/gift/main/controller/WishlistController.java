package gift.main.controller;

import gift.main.annotation.SessionUser;
import gift.main.dto.UserVo;
import gift.main.dto.WishProductResponce;
import gift.main.service.ProductService;
import gift.main.service.WishProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class WishlistController {

    private final WishProductService wishProductService;
    private final ProductService productService;

    public WishlistController(WishProductService wishProductService, ProductService productService) {
        this.wishProductService = wishProductService;
        this.productService = productService;
    }

    @DeleteMapping("/wishlist/{productId}")
    @Transactional
    public ResponseEntity<?> deleteWishProduct(@PathVariable(name = "productId") Long productId, @SessionUser UserVo sessionUserVo) {
        wishProductService.deleteProducts(productId, sessionUserVo);
        return ResponseEntity.ok("successfully deleted the item to your wishlist");
    }

    @GetMapping("/wishlists")
    public ResponseEntity<?> getWishProduct(@SessionUser UserVo sessionUser) {
        List<WishProductResponce> wishProducts = wishProductService.getWishProducts(sessionUser.getId());
        return ResponseEntity.ok(wishProducts);
    }

    @GetMapping("/wishlistpage/{pageNum}/{productNum}")
    public ResponseEntity<?> getWishProductPage(
            @SessionUser UserVo sessionUser,
            @PathVariable(name = "pageNum") Optional<Integer> pageNum,
            @PathVariable(name = "productNum") Optional<Integer> productNum) {
        Page<WishProductResponce> wishProductPage = wishProductService.getWishProductPage(
                sessionUser.getId(),
                pageNum.orElse(0),
                productNum.orElse(10));
        return ResponseEntity.ok(wishProductPage);
    }

    @Transactional
    @PostMapping("/wishlist/{productId}")
    public ResponseEntity<?> addWishlistProduct(@PathVariable(name = "productId") Long productId, @SessionUser UserVo sessionUser) {
        wishProductService.addWishlistProduct(productId, sessionUser);
        return ResponseEntity.ok("successfully added the item to your wishlist");
    }

}
