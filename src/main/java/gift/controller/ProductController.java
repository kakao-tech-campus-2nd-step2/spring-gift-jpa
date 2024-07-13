package gift.controller;
import gift.entity.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/products")
public class ProductController {
  private ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public ResponseEntity<List<Product>> getAllProducts() {
    List<Product> products = productService.findAll();
    return ResponseEntity.ok(products);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable long id) {
    Product product = productService.getProductById(id);
    return ResponseEntity.ok(product);
  }

  @PostMapping
  public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
    Product createdProduct = productService.addProduct(product);
    return ResponseEntity.ok(createdProduct);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable long id, @Valid @RequestBody Product product) {
    product.setId(id);
    Product updatedProduct = productService.updateProduct(product);
    return ResponseEntity.ok(updatedProduct);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteProduct(@PathVariable long id) {
    productService.deleteProduct(id);
    return ResponseEntity.ok().build();
  }
}
