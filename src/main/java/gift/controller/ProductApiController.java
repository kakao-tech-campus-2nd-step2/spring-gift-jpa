package gift.controller;

import gift.domain.model.ProductDto;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    //    id로 상품 조회
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    //    전체 상품 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getAllProducts() {
        return productService.getAllProduct();
    }

    //    상품 추가
    @PostMapping
    public ResponseEntity<Map<String, Object>> addProduct(
        @Valid @RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.addProduct(productDto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "상품이 성공적으로 추가되었습니다.");
        response.put("data", createdProduct);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //    상품 수정
    @PutMapping
    public ResponseEntity<Map<String, Object>> updateProduct(
        @Valid @RequestBody ProductDto productDto) {
        productService.updateProduct(productDto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "상품이 성공적으로 수정되었습니다.");
        response.put("data", productDto);

        return ResponseEntity.ok(response);
    }

    //    상품 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
