package gift.controller;

import gift.dto.CreateProductDto;
import gift.dto.ProductDeleteDto;
import gift.dto.ProductDto;
import gift.dto.UpdateProductDto;
import gift.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        ProductDto productDto = productService.getProduct(id);
        return ResponseEntity.ok(productDto);

    }

    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody CreateProductDto giftDto) {
        Long createdId = productService.createProduct(giftDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdId);
    }

    @PutMapping
    public ResponseEntity<Long> updateProduct(@RequestBody UpdateProductDto giftDto) {
        Long updatedId = productService.updateProduct(giftDto);
        return ResponseEntity.ok(updatedId);
    }

    @DeleteMapping
    public ResponseEntity<Long> deleteProduct(@RequestParam Long id) {
        Long deletedId = productService.deleteProduct(id);
        return ResponseEntity.ok(deletedId);
    }

    @DeleteMapping("/manager")
    public ResponseEntity<Void> deleteProducts(@RequestBody ProductDeleteDto productDeleteDto) {
        productService.deleteProducts(productDeleteDto.productIds());
        return ResponseEntity.ok().build();
    }

}
