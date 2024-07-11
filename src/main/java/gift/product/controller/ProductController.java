package gift.product.controller;

import gift.product.model.dto.CreateProductRequest;
import gift.product.model.dto.Product;
import gift.product.model.dto.ProductResponse;
import gift.product.model.dto.UpdateProductRequest;
import gift.product.service.ProductService;
import gift.user.model.dto.User;
import gift.user.resolver.LoginUser;
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

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable Long id) {
        final Product product = productService.findProduct(id);
        ProductResponse response = new ProductResponse(product.getId(), product.getName(), product.getPrice(),
                product.getImageUrl());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAllProduct() {
        final List<ProductResponse> response = productService.findAllProduct();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@LoginUser User loginUser,
                                             @Valid @RequestBody CreateProductRequest createProductRequest) {
        productService.addProduct(loginUser, createProductRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@LoginUser User loginUser, @PathVariable Long id,
                                                @Valid @RequestBody UpdateProductRequest updateProductRequest) {
        productService.updateProduct(loginUser, id, updateProductRequest);
        return ResponseEntity.ok().body("ok");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@LoginUser User loginUser, @PathVariable Long id) {
        productService.deleteProduct(loginUser, id);
        return ResponseEntity.ok().body("ok");
    }
}
