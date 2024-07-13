package gift.controller;

import gift.model.ProductDto;
import gift.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public ResponseEntity<Page<ProductDto>> getAllProducts(Pageable pageable) {
    return ResponseEntity.ok(productService.findAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
    return productService.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto product) {
    return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto product) {
    if (productService.updateProduct(id, product)) {
      return ResponseEntity.ok(product);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    if (!productService.findById(id).isPresent()) {
      return ResponseEntity.notFound().build();
    }
    productService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
