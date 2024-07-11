package gift.controller;

import gift.domain.Product;
import gift.dto.request.AddProductRequest;
import gift.dto.request.UpdateProductRequest;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable("productId") Long productId) {
        return productService.getProduct(productId);
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@Valid @RequestBody AddProductRequest newProduct) {
        return ResponseEntity.ok(productService.addProduct(newProduct));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable("productId") Long productId, @Valid @RequestBody UpdateProductRequest product) {
        return ResponseEntity.ok(productService.updateProduct(productId, product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

}

