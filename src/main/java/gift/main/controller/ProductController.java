package gift.main.controller;

import gift.main.annotation.AdminCheck;
import gift.main.annotation.SessionUser;
import gift.main.dto.ProductRequest;
import gift.main.dto.ProductResponce;
import gift.main.dto.UserVo;
import gift.main.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProducts(@SessionUser UserVo sessionUserVo) {
        List<ProductResponce> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> findProduct(@PathVariable(name = "id") Long id, @SessionUser UserVo sessionUserVo) {
        ProductResponce product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/product")
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductRequest productRequest, @SessionUser UserVo sessionUserVo) {
        productService.addProduct(productRequest, sessionUserVo);
        return ResponseEntity.ok("Product added successfully");
    }

    @PutMapping("/product")
    public ResponseEntity<?> updateProduct(
            @RequestParam(value = "id") long id,
            @Valid @RequestBody ProductRequest productRequest,
            @SessionUser UserVo sessionUserVo) {

        productService.updateProduct(id, productRequest);
        return ResponseEntity.ok("Product added successfully");
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }


}
