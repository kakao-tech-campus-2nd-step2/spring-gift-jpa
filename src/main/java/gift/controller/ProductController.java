package gift.controller;

import gift.dto.ProductDTO;
import gift.entity.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Description("get all products")
    @GetMapping("/products")
    public ResponseEntity<Collection<Product>> getProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @Description("get product by id")
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId) {
        Product product = productService.getProduct(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(product);
        //return ResponseEntity.ok(product);
    }

    @Description("add product : id는 자동 추가")
    @PostMapping("/products")
    public ResponseEntity<String> addProduct(@RequestBody @Valid ProductDTO productDTO) {
        productService.saveProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Product added successfully");
    }


    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Product deleted successfully");
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody @Valid ProductDTO productDTO) {
        productService.updateProduct(productId, productDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Product updated successfully");
    }

}

