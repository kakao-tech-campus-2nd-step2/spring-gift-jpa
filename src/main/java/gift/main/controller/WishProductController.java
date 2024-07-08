package gift.main.controller;

import gift.main.dto.UserVo;
import gift.main.dto.WishListProductDto;
import gift.main.entity.Product;
import gift.main.entity.WishlistProduct;
import gift.main.repository.ProductDao;
import gift.main.repository.WishlistProductDao;
import gift.main.util.AuthUtil;
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
    public ResponseEntity<?> deleteProducts(@PathVariable(name = "productId") Long productId, HttpSession session) {
        UserVo seesionUser = AuthUtil.getSessionUser(session);
        wishlistProductDao.deleteWishlistProductByUserIdAndProductId(seesionUser.getId(), productId);
        return ResponseEntity.ok("성공적으로 삭제 완료~!");
    }

    @GetMapping("/wishlist")
    public ResponseEntity<?> deleteProducts(HttpSession session) {
        UserVo seesionUser = AuthUtil.getSessionUser(session);
        List<WishlistProduct> wishlistProducts = wishlistProductDao.selectWishlistProductsByUserId(seesionUser.getId());
        Map<String, List<WishlistProduct>> response = new HashMap<>();
        response.put("wishlistProducts", wishlistProducts);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/wishlist/{productId}")
    public ResponseEntity<?> addWishlistProduct(@PathVariable(name = "productId") Long productId, HttpSession session){
        UserVo seesionUser = AuthUtil.getSessionUser(session);
        WishListProductDto wishListProductDto = new WishListProductDto(productId, seesionUser.getId());
        wishlistProductDao.insertWishlistProduct(wishListProductDto);
        return ResponseEntity.ok("성공적으로 등록~");
    }

}
