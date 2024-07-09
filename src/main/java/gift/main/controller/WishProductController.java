package gift.main.controller;

import gift.main.annotation.SessionUser;
import gift.main.dto.UserVo;
import gift.main.dto.WishListProductDto;
import gift.main.entity.Product;
import gift.main.entity.WishlistProduct;
import gift.main.repository.ProductDao;
import gift.main.repository.WishlistProductDao;
import gift.main.util.AuthUtil;
import gift.main.util.JwtUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class WishProductController {
    private final ProductDao productDao;
    private final WishlistProductDao wishlistProductDao;

    public WishProductController(ProductDao productDao, WishlistProductDao wishlistProductDao) {
        this.productDao = productDao;
        this.wishlistProductDao = wishlistProductDao;
    }

    @GetMapping()
    public ResponseEntity<?> getProducts() {
        List<Product> products = productDao.selectProductAll();
        Map<String, List<Product>> response = new HashMap<>();
        response.put("products", products);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/wishlist/{productId}")
    public ResponseEntity<?> deleteProducts(@PathVariable(name = "productId") Long productId, @SessionUser UserVo sessionUserVo) {
        wishlistProductDao.deleteWishlistProductByUserIdAndProductId(sessionUserVo.getId(), productId);
        return ResponseEntity.ok("성공적으로 삭제 완료~!");
    }

    @GetMapping("/wishlist")
    public ResponseEntity<?> deleteProducts(@SessionUser UserVo sessionUser) {
        List<WishlistProduct> wishlistProducts = wishlistProductDao.selectWishlistProductsByUserId(sessionUser.getId());
        Map<String, List<WishlistProduct>> response = new HashMap<>();
        response.put("wishlistProducts", wishlistProducts);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/wishlist/{productId}")
    public ResponseEntity<?> addWishlistProduct(@PathVariable(name = "productId") Long productId, @SessionUser UserVo sessionUser){
        WishListProductDto wishListProductDto = new WishListProductDto(productId, sessionUser.getId());
        wishlistProductDao.insertWishlistProduct(wishListProductDto);
        return ResponseEntity.ok("성공적으로 등록~");
    }

}
