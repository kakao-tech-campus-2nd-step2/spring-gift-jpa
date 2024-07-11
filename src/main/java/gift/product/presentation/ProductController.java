package gift.product.presentation;

import gift.product.application.ProductResponse;
import gift.product.application.ProductService;
import gift.product.presentation.request.ProductCreateRequest;
import gift.product.presentation.request.ProductUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public void create(@Valid @RequestBody ProductCreateRequest request) {
        productService.save(request.toCommand());
    }

    @GetMapping("")
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(
            @PathVariable("id") Long productId
    ) {
        return ResponseEntity.ok(productService.findById(productId));
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable("id") Long productId,
            @Valid @RequestBody ProductUpdateRequest request
    ) {
        productService.update(request.toCommand(productId));
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id") Long productId
    ) {
        productService.delete(productId);
    }
}