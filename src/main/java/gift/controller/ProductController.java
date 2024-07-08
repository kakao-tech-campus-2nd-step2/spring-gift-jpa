package gift.controller;

import gift.entity.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<Collection<Product>> getProducts() {
        return productService.selectAllProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
        return productService.selectProductById(id);
    }


    @PostMapping("/products")
    public ResponseEntity<String> addProduct(@RequestBody @Valid Product product) {
        return productService.saveProduct(product);
    }


    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody @Valid Product updateProduct) {
        return productService.updateProduct(id, updateProduct);
    }

}

