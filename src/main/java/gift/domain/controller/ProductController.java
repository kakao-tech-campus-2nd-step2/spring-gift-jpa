package gift.domain.controller;

import gift.domain.dto.ProductRequestDto;
import gift.domain.dto.ProductResponseDto;
import gift.domain.service.ProductService;
import gift.global.response.SuccessResponse;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getProducts() {
        return SuccessResponse.ok(productService.getAllProducts(), "products");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addProduct(@Valid @RequestBody ProductRequestDto requestDto) {
        ProductResponseDto responseDto = productService.addProduct(requestDto);
        return SuccessResponse.created(
            responseDto,
            "created-product",
            "/api/products/{id}",
            responseDto.id());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable("id") Long id,
                                                             @Valid @RequestBody ProductRequestDto requestDto) {
        productService.updateProductById(id, requestDto);
        return SuccessResponse.ok();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return SuccessResponse.ok();
    }
}
