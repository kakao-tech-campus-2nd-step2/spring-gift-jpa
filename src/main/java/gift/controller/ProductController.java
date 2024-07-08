package gift.controller;

import gift.request.ProductRequest;
import gift.response.ProductResponse;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/products")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> productList() {
        return ResponseEntity.ok()
            .body(productService.getProducts());
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
