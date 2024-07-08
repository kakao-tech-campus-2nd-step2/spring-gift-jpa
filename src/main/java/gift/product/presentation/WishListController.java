package gift.product.presentation;


import gift.product.application.WishListService;
import gift.product.domain.Product;
import gift.product.exception.ProductException;
import gift.util.CommonResponse;
import gift.util.ErrorCode;
import gift.util.JwtAuthenticated;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @JwtAuthenticated
    @GetMapping("/{userId}")
    public ResponseEntity<?> getWishList(@PathVariable Long userId) {
        List<Product> products = wishListService.getProductsInWishList(userId);
        if (products != null) {
            return ResponseEntity.ok(new CommonResponse<>(products, "위시리스트 조회 성공", true));
        } else {
            throw new ProductException(ErrorCode.WISHLIST_NOT_FOUND);
        }
    }

    @JwtAuthenticated
    @PostMapping("/{userId}/add")
    public ResponseEntity<?> addProductToWishList(@PathVariable Long userId, @RequestBody Product product) {
        wishListService.addProductToWishList(userId, product);
        return ResponseEntity.ok(new CommonResponse<>(null, "위시리스트에 제품이 추가되었습니다", true));
    }

    @JwtAuthenticated
    @DeleteMapping("/{userId}/delete/{productId}")
    public ResponseEntity<?> deleteProductFromWishList(@PathVariable Long userId, @PathVariable Long productId) {
        wishListService.deleteProductFromWishList(userId, productId);
        return ResponseEntity.ok(new CommonResponse<>(null, "위시리스트에서 제품이 삭제되었습니다", true));
    }
}
