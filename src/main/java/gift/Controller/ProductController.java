package gift.Controller;

import gift.DTO.ProductEntity;
import gift.Service.ProductService;
import jakarta.validation.Valid;
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

  @GetMapping
  public List<ProductDto> getAllProducts() {
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
  public ProductDto addProduct(@Valid @RequestBody ProductDto productDto) {
    return productService.addProduct(productDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,
    @RequestBody ProductDto updatedProductDto) {
    ProductDto existingProductDto = productService.updateProduct(id,
      updatedProductDto);
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
