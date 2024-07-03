package gift.Controller;

import gift.DTO.productDto;
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
  public List<productDto> getAllProducts() {
    return productService.getAllProducts();
  }

  @GetMapping("/{id}")
  public ResponseEntity<productDto> getProductById(@PathVariable Long id) {
    productDto productDTO = productService.getProductById(id);
    if (productDTO == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(productDTO);
  }

  @PostMapping
  public productDto addProduct(@Valid @RequestBody productDto productDTO) {
    return productService.addProduct(productDTO);
  }

  @PutMapping("/{id}")
  public ResponseEntity<productDto> updateProduct(@PathVariable Long id, @Valid @RequestBody productDto updatedProductDto)
  {
    productDto existingProductDto = productService.updateProduct(id, updatedProductDto);
    if (existingProductDto == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(updatedProductDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productDto existingProductDto = productService.deleteProduct(id);
    if (existingProductDto == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.noContent().build();
  }

}
