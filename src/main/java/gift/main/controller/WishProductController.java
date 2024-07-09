package gift.main.controller;

import gift.main.annotation.SessionUser;
import gift.main.dto.UserVo;
import gift.main.entity.Product;
import gift.main.entity.WishProduct;
import gift.main.repository.ProductRepository;
import gift.main.repository.WishProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class WishProductController {
    private final WishProductRepository wishProductRepository;
    private final ProductRepository productRepository;

    public WishProductController(WishProductRepository wishProductRepository, ProductRepository productRepository) {
        this.wishProductRepository = wishProductRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<?> getProducts() {
        List<Product> products = productRepository.findAll();
        Map<String, List<Product>> response = new HashMap<>();
        response.put("products", products);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/wishlist/{productId}")
    @Transactional
    public ResponseEntity<?> deleteProducts(@PathVariable(name = "productId") Long productId, @SessionUser UserVo sessionUserVo) {
        wishProductRepository.deleteByProductIdAndUserId(productId, sessionUserVo.getId());
        return ResponseEntity.ok("성공적으로 삭제 완료~!");
    }

    @GetMapping("/wishlist")
    public ResponseEntity<?> deleteProducts(@SessionUser UserVo sessionUser) {
        List<WishProduct> wishProducts = wishProductRepository.findAllByUserId(sessionUser.getId());
        Map<String, List<WishProduct>> response = new HashMap<>();
        response.put("wishlistProducts", wishProducts);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping("/wishlist/{productId}")
    public ResponseEntity<?> addWishlistProduct(@PathVariable(name = "productId") Long productId, @SessionUser UserVo sessionUser){
        WishProduct wishProduct = new WishProduct(productId, sessionUser.getId());
        wishProductRepository.save(wishProduct);
        return ResponseEntity.ok("성공적으로 등록~");
    }

}
