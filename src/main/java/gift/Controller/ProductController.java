package gift.Controller;

import gift.DTO.ProductDto;
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
  public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
    ProductDto productDTO = productService.getProductById(id);

    if (productDTO == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(productDTO);
  }

  @PostMapping
  public ProductDto addProduct(@Valid @RequestBody ProductDto productDTO) {
    return productService.addProduct(productDTO);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,
    @Valid @RequestBody ProductDto updatedProductDto) {
    ProductDto existingProductDto = productService.updateProduct(id, updatedProductDto);
    if (existingProductDto == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(updatedProductDto);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id) {
    ProductDto existingProductDto = productService.deleteProduct(id);
    return ResponseEntity.ok(existingProductDto);
  }
}
