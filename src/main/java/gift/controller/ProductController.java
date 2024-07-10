package gift.controller;

import gift.exception.ProductNotFoundException;
import gift.model.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "All products retrieved successfully.");
        response.put("products", products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Product product = productService.getProductById(id);
            response.put("message", "Product retrieved successfully.");
            response.put("product", product);
            return ResponseEntity.ok(response);
        } catch (ProductNotFoundException ex) {
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createProduct(@Valid @RequestBody Product product) {
        boolean success = productService.createProduct(product);
        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("message", "Product created successfully.");
            response.put("product", product);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        response.put("message", "Failed to create product.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        boolean success = productService.updateProduct(id, product);
        Map<String, Object> response = new HashMap<>();
        if (success) {
            Product updatedProduct = productService.getProductById(id);
            response.put("message", "Product updated successfully.");
            response.put("product", updatedProduct);
            return ResponseEntity.ok(response);
        }
        response.put("message", "Failed to update product.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> patchProduct(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        boolean success = productService.patchProduct(id, updates);
        Map<String, Object> response = new HashMap<>();
        if (success) {
            Product updatedProduct = productService.getProductById(id);
            response.put("message", "Product patched successfully.");
            response.put("product", updatedProduct);
            return ResponseEntity.ok(response);
        }
        response.put("message", "Failed to patch product.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @PatchMapping
    public ResponseEntity<Map<String, Object>> patchProducts(@RequestBody List<Map<String, Object>> updatesList) {
        List<Optional<Product>> updatedProducts = productService.patchProducts(updatesList);
        Map<String, Object> response = new HashMap<>();
        int originalCount = updatesList.size();
        int updateCount = updatedProducts.size();

        response.put("updatedProducts", updatedProducts);

        if (updateCount == originalCount) {
            response.put("message", "All products patched successfully.");
            return ResponseEntity.ok(response);
        }

        if (updateCount > 0) {
            response.put("message", "Some products patched successfully.");
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
        }

        response.put("message", "No products patched.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Long id) {
        boolean success = productService.deleteProduct(id);
        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("message", "Product deleted successfully.");
            return ResponseEntity.noContent().build();
        }
        response.put("message", "Failed to delete product.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
