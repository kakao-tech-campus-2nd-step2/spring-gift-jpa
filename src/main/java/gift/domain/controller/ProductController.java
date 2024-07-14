package gift.domain.controller;

import gift.domain.controller.apiResponse.ProductAddApiResponse;
import gift.domain.controller.apiResponse.ProductListApiResponse;
import gift.domain.dto.request.ProductRequest;
import gift.domain.service.ProductService;
import gift.global.apiResponse.BasicApiResponse;
import gift.global.apiResponse.SuccessApiResponse;
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
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ProductListApiResponse> getProducts() {
        return SuccessApiResponse.ok(new ProductListApiResponse(HttpStatus.OK, productService.getAllProducts()));
    }

    @PostMapping
    public ResponseEntity<ProductAddApiResponse> addProduct(@Valid @RequestBody ProductRequest requestDto) {
        var result = productService.addProduct(requestDto);
        return SuccessApiResponse.created(
            new ProductAddApiResponse(HttpStatus.CREATED, result),
            "/api/products/{id}",
            result.id());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicApiResponse> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductRequest requestDto) {
        productService.updateProductById(id, requestDto);
        return SuccessApiResponse.ok();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicApiResponse> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return SuccessApiResponse.ok();
    }
}
