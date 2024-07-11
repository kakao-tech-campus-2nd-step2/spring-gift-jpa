package gift.Controller;

import gift.DTO.ProductEntity;
import gift.Service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
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

  @GetMapping
  public List<ProductEntity> getAllProducts() {
    return productService.getAllProducts();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<ProductEntity>> getProductById(@PathVariable Long id) {
    Optional<ProductEntity> productDTO = productService.getProductById(id);

    if (productDTO == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(productDTO);
  }

  @PostMapping
  public ProductEntity addProduct(@Valid @RequestBody ProductEntity productEntity) {
    return productService.addProduct(productEntity);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long id,
    @Valid @RequestBody ProductEntity updatedProductEntity) {
    Optional<ProductEntity> existingProductDto = productService.updateProduct(id,
      updatedProductEntity);
    if (existingProductDto == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(updatedProductEntity);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Optional<ProductEntity>> deleteProduct(@PathVariable Long id) {
    Optional<ProductEntity> existingProductDto = productService.deleteProduct(id);
    return ResponseEntity.ok(existingProductDto);
  }
}
