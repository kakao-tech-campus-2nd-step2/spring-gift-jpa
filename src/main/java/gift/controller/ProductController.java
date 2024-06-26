package gift.controller;

import gift.domain.Product;
import gift.dto.CreateProductDto;
import gift.service.ProductService;
import gift.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ValidationService validationService;

    public ProductController(ProductService productService, ValidationService validationService) {
        this.productService = productService;
        this.validationService = validationService;
    }

    // 상품 추가
    @PostMapping
    public Product createProduct(@RequestBody CreateProductDto productDto) {
        validationService.validateProductDto(productDto);
        return productService.createProduct(productDto);
    }

    // 전체 상품 조회
    @GetMapping
    public List<Product> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        validationService.validateProductList(products);
        return products;
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long product_id) {
        Product product = productService.getProduct(product_id);
        validationService.validateProduct(product);
        return ResponseEntity.ok(product);
    }

}
