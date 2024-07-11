package gift.main.controller;

import gift.main.annotation.SessionUser;
import gift.main.dto.UserVo;
import gift.main.entity.Product;
import gift.main.entity.WishProduct;
import gift.main.service.ProductService;
import gift.main.service.WishProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class WishProductController {

    private final WishProductService wishProductService;
    private final ProductService productService;

    public WishProductController(WishProductService wishProductService, ProductService productService) {
        this.wishProductService = wishProductService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getProducts() {
        List<Product> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/wishlist/{productId}")
    @Transactional
    public ResponseEntity<?> deleteWishProduct(@PathVariable(name = "productId") Long productId, @SessionUser UserVo sessionUserVo) {
        wishProductService.deleteProducts(productId, sessionUserVo);
        return ResponseEntity.ok("성공적으로 삭제 완료~!");
    }

    @GetMapping("/wishlist")
    public ResponseEntity<?> getWishProduct(@SessionUser UserVo sessionUser) {
        List<WishProduct> wishProducts = wishProductService.getWishProducts(sessionUser.getId());
        return ResponseEntity.ok(wishProducts);
    }

    @Transactional
    @PostMapping("/wishlist/{productId}")
    public ResponseEntity<?> addWishlistProduct(@PathVariable(name = "productId") Long productId, @SessionUser UserVo sessionUser){
        wishProductService.addWishlistProduct(productId, sessionUser);
        return ResponseEntity.ok("성공적으로 등록~");
    }

}
