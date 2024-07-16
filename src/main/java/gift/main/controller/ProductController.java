package gift.main.controller;

import gift.main.dto.ProductResponce;
import gift.main.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProductPage(Pageable pageable) {
        Page<ProductResponce> productPage = productService.getProductPage(pageable);
        return ResponseEntity.ok(productPage);
    }
}
