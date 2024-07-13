package gift.Controller;

import gift.DTO.ProductDto;
import gift.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  public ResponseEntity<Page<ProductDto>> getAllProducts(Pageable pageable) {

    return ResponseEntity.ok(productService.getAllProducts(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
    ProductDto productDTO = productService.getProductById(id);

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

    return ResponseEntity.ok(updatedProductDto);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id) {
    ProductDto existingProductDto = productService.deleteProduct(id);

    return ResponseEntity.ok(existingProductDto);
  }
}
