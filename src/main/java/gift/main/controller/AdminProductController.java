package gift.main.controller;

import gift.main.annotation.SessionUser;
import gift.main.dto.ProductRequest;
import gift.main.dto.ProductResponce;
import gift.main.dto.UserVo;
import gift.main.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/product")
public class AdminProductController {
    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProduct(@PathVariable(name = "id") Long id, @SessionUser UserVo sessionUserVo) {
        ProductResponce product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductRequest productRequest, @SessionUser UserVo sessionUserVo) {
        productService.addProduct(productRequest, sessionUserVo);
        return ResponseEntity.ok("Product added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable(value = "id") long id,
            @Valid @RequestBody ProductRequest productRequest,
            @SessionUser UserVo sessionUserVo) {
        System.out.println("productRequest = " + productRequest);
        productService.updateProduct(id, productRequest);
        return ResponseEntity.ok("Product added successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }


}
