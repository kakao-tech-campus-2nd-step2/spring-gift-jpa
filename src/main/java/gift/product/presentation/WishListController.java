package gift.product.presentation;


import gift.product.application.WishListService;
import gift.product.domain.WishList;
import gift.product.exception.ProductException;
import gift.util.CommonResponse;
import gift.util.ErrorCode;
import gift.util.JwtAuthenticated;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @JwtAuthenticated
    @GetMapping("/{userId}")
    public ResponseEntity<?> getWishList(@PathVariable Long userId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction) {
        Page<WishList> products = wishListService.getProductsInWishList(userId, page, size, sortBy, direction);
        return ResponseEntity.ok(new CommonResponse<>(products, "위시리스트 조회 성공", true));
    }

    @JwtAuthenticated
    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<?> addProductToWishList(@PathVariable Long userId, @PathVariable Long productId) {
        wishListService.addProductToWishList(userId, productId);
        return ResponseEntity.ok(new CommonResponse<>(null, "위시리스트에 제품이 추가되었습니다", true));
    }

    @JwtAuthenticated
    @DeleteMapping("/{userId}/delete/{productId}")
    public ResponseEntity<?> deleteProductFromWishList(@PathVariable Long userId, @PathVariable Long productId) {
        wishListService.deleteProductFromWishList(userId, productId);
        return ResponseEntity.ok(new CommonResponse<>(null, "위시리스트에서 제품이 삭제되었습니다", true));
    }
}
