package gift.controller;

import gift.request.ProductRequest;
import gift.response.ProductResponse;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/products")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> productList(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductResponse> products = productService.getProducts(pageable);

        return ResponseEntity.ok()
                .body(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> productOne(@PathVariable Long productId) {
        return ResponseEntity.ok()
                .body(productService.getProduct(productId));
    }

    @PostMapping
    public ResponseEntity<Void> productAdd(@RequestBody @Valid ProductRequest request) {
        productService.addProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> productEdit(@PathVariable Long productId,
                                            @RequestBody @Valid ProductRequest request) {
        productService.editProduct(productId, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> productRemove(@PathVariable Long productId) {
        productService.removeProduct(productId);

        return ResponseEntity.ok().build();
    }

}
