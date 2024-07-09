package gift.controller.product;

import gift.dto.request.ProductRequest;
import gift.dto.response.AddedProductIdResponse;
import gift.dto.response.ProductResponse;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("api/products")
    public ResponseEntity<AddedProductIdResponse> addProduct(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.addProduct(request.getName(), request.getPrice(), request.getImageUrl()));
    }

    @GetMapping("api/products")
    public List<ProductResponse> getProducts() {
        return productService.getProducts();
    }

    @PutMapping("api/products")
    public ResponseEntity<Void> updateProduct(@Valid @RequestBody ProductRequest request) {
        productService.updateProduct(request.getId(), request.getName(), request.getPrice(), request.getImageUrl());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("api/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
