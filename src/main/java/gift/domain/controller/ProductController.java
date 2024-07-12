package gift.domain.controller;

import gift.domain.dto.ProductAddResponseDto;
import gift.domain.dto.ProductListResponseDto;
import gift.domain.dto.ProductRequestDto;
import gift.domain.dto.ProductResponseDto;
import gift.domain.service.ProductService;
import gift.global.response.BasicResponse;
import gift.global.response.SuccessResponse;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ProductListResponseDto> getProducts() {
        return SuccessResponse.ok(new ProductListResponseDto(HttpStatus.OK, productService.getAllProducts()));
    }

    @PostMapping
    public ResponseEntity<ProductAddResponseDto> addProduct(@Valid @RequestBody ProductRequestDto requestDto) {
        var result = productService.addProduct(requestDto);
        return SuccessResponse.created(
            new ProductAddResponseDto(HttpStatus.CREATED, result),
            "/api/products/{id}",
            result.id());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicResponse> updateProduct(@PathVariable("id") Long id,
                                                             @Valid @RequestBody ProductRequestDto requestDto) {
        productService.updateProductById(id, requestDto);
        return SuccessResponse.ok();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponse> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return SuccessResponse.ok();
    }
}
