package gift.controller;

import gift.common.annotation.LoginUser;
import gift.model.product.ProductListResponse;
import gift.model.product.ProductRequest;
import gift.model.product.ProductResponse;
import gift.model.user.User;
import gift.service.ProductService;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<ProductResponse> registerProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse response = productService.register(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/products")
    public ResponseEntity<ProductListResponse> getAllProducts() {
        ProductListResponse responses = productService.findAllProduct();
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Long id) {
        ProductResponse response = productService.findProduct(id);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Long id,
                                                         @Valid @RequestBody ProductRequest productRequest) {
        ProductResponse response = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity deleteProduct(@LoginUser User user, @PathVariable("id") Long id) {
        productService.deleteProduct(user.getId(), id);
        return ResponseEntity.noContent().build();
    }
}

