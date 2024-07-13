package gift.main.controller;

import gift.main.dto.ProductResponce;
import gift.main.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProducts() {
        List<ProductResponce> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }
}
