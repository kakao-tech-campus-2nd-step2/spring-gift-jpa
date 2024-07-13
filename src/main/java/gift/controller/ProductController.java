package gift.controller;

import gift.domain.Product;
import gift.dto.request.AddProductRequest;
import gift.dto.request.UpdateProductRequest;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        Page<Product> products = productService.getAllProducts(page);
        return ResponseEntity.ok(products);
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

