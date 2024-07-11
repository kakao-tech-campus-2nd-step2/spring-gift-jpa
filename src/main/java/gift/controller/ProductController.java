package gift.controller;

import gift.domain.product.Product;
import gift.domain.product.ProductRequest;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> readProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product readProduct(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(
        @Valid @RequestBody ProductRequest productRequest) {
        Product product = productService.save(productRequest);
        return ResponseEntity.created(URI.create("/api/products/" + product.getId())).body(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id,
        @Valid @RequestBody ProductRequest productRequest) {
        return productService.update(id, productRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}