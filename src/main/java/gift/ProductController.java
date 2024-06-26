package gift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService productService;
  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public List<Product> getAllProducts() {
    return productService.getAllProducts();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    Product product = productService.getProductById(id);
    if (product != null) {
      return ResponseEntity.ok(product);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public Product addProduct(@RequestBody Product product) {
    return productService.addProduct(product);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
    Product product = productService.updateProduct(id, updatedProduct);
    if (product != null) {
      return ResponseEntity.ok(product);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    Product product = productService.deleteProduct(id);
    if (product != null) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
