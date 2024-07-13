package gift.main.controller;

import gift.main.dto.ProductResponce;
import gift.main.service.ProductService;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<?> getProducts() {
        List<ProductResponce> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/productpage/{pageNum}")
    public ResponseEntity<?> getProducts(@PathVariable(name = "pageNum")Optional<Integer> pageNum) {
        Page<ProductResponce> productPage = productService.getProductPage(pageNum.orElse(0));
        return ResponseEntity.ok(productPage);
    }
}
