package gift.product.controller;

import gift.product.model.Product;
import gift.product.service.ProductService;
import gift.product.validation.ProductValidation;
import jakarta.validation.Valid;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ApiProductController {

    private final ProductService productService;
    private final ProductValidation productValidation;

    @Autowired
    public ApiProductController(ProductService productService, ProductValidation productValidation) {
        this.productService = productService;
        this.productValidation = productValidation;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> showProductList() {
        System.out.println("[ProductController] showProductList()");
        List<Product> productList = new ArrayList<>(productService.getAllProducts());
        return ResponseEntity.ok(productList);
    }

    @PostMapping()
    public ResponseEntity<String> registerProduct(@Valid @RequestBody Product product) {
        System.out.println("[ProductController] registerProduct()");
        return productService.registerProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        System.out.println("[ProductController] updateProduct()");
        return productService.updateProduct(new Product(id, product.getName(),product.getPrice(), product.getImageUrl()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        System.out.println("[ProductController] deleteProduct()");
        if (productService.existsById(id)) {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam("keyword") String keyword) {
        System.out.println("[ProductController] searchProduct()");
        List<Product> searchResults = new ArrayList<>(productService.searchProducts(keyword));
        return ResponseEntity.ok(searchResults);
    }
}
