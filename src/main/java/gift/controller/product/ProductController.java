package gift.controller.product;

import gift.custom_annotation.annotation.PageInfo;
import gift.dto.product.ProductPatchDTO;
import gift.dto.product.ProductResponseDTO;
import gift.dto.product.ProductRequestDTO;
import gift.entity.Product;
import gift.repository.ProductRepository;
import gift.util.pagenation.PageInfoDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ProductController {
    private final gift.service.ProductService productService;

    public ProductController(gift.service.ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(
            @PageInfo(entityClass = Product.class)
            PageInfoDTO pageInfoDTO
    ) {
        return ResponseEntity.ok(productService.getAllProducts(pageInfoDTO));
    }

    @PostMapping("/api/products")
    public ResponseEntity<ProductResponseDTO> addProduct(@Valid @RequestBody ProductRequestDTO product) {
        ProductResponseDTO result = productService.addProduct(product);

        return makeCreatedResponse(result);
    }

    @DeleteMapping("/api/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/api/products/{id}")
    public ResponseEntity<ProductResponseDTO> updateProductPartially(@PathVariable long id, @Valid @RequestBody ProductPatchDTO patch) {
        ProductResponseDTO productResponseDTO = productService.updateProduct(id, patch);

        return ResponseEntity.ok(productResponseDTO);
    }

    private ResponseEntity<ProductResponseDTO> makeCreatedResponse(ProductResponseDTO product) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/products/"+ product.id())
                .build()
                .toUri();

        return ResponseEntity.created(location).body(product);
    }
}
