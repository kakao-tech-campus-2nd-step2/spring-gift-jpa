package gift.controller;

import gift.model.Product;
import gift.model.ProductDTO;
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

    @GetMapping()
    public List<Product> getAllProducts() {
        List<Product> products = productService.findAll();
        return products;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Product> postProduct(@RequestBody @Valid ProductDTO form) {
        Product result = productService.save(new Product(form));
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<Product> putProduct(@RequestBody @Valid ProductDTO form,
                                              @PathVariable("id") Long id) {
        Product result = productService.update(id, form);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        productService.delete(id);
        return ResponseEntity.ok().body("deleted successfully");
    }
}
