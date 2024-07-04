package gift.Controller;

import gift.DTO.ProductDTO;
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
  public List<ProductDTO> getAllProducts() {
    return productService.getAllProducts();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
    ProductDTO productDTO = productService.getProductById(id);
    if (productDTO == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(productDTO);
  }

  @PostMapping
  public ProductDTO addProduct(@Valid @RequestBody ProductDTO productDTO) {
    return productService.addProduct(productDTO);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO updatedProductDTO)
  {
    ProductDTO existingProductDTO = productService.updateProduct(id, updatedProductDTO);
    if (existingProductDTO == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(updatedProductDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    ProductDTO existingProductDTO = productService.deleteProduct(id);
    if (existingProductDTO == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.noContent().build();
  }
}
