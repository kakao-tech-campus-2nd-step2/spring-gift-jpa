package gift.main.controller;

import gift.main.dto.ProductRequest;
import gift.main.entity.Product;
import gift.main.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/list")
    public ResponseEntity<Map<String, List<Product>>> getProducts() {
        List<Product> products = productService.getProducts();
        Map<String, List<Product>> response = new HashMap<>();
        response.put("products", products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> findProduct(@PathVariable(name = "id") Long id) {
        Product product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/product")

    public ResponseEntity<String> addProduct(@Valid  @RequestBody ProductRequest productRequest) {
        productService.addProduct(productRequest);
        return ResponseEntity.ok("Product added successfully");
    }

    @PutMapping("/product")

    public ResponseEntity<?>  updateProduct(@RequestParam(value = "id") long id,@Valid @RequestBody ProductRequest productRequest) {

        productService.updateProduct(id, productRequest);
        return ResponseEntity.ok("Product updated successfully");
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }


}
