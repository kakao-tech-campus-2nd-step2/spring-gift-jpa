package gift.product.controller;

import gift.product.model.Product;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ApiProductController {

    private final ProductService productService;

    @Autowired
    public ApiProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public Page<Product> showProductList(Pageable pageable) {
        System.out.println("[ProductController] showProductList()");
        return productService.getAllProducts(pageable);
    }

    @PostMapping()
    public ResponseEntity<String> registerProduct(@Valid @RequestBody Product product) {
        System.out.println("[ProductController] registerProduct()");
        return productService.registerProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        System.out.println("[ProductController] updateProduct()");
        return productService.updateProduct(id, new Product(product.getName(),product.getPrice(), product.getImageUrl()));
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
    public Page<Product> searchProduct(@RequestParam("keyword") String keyword, Pageable pageable) {
        System.out.println("[ProductController] searchProduct()");
        return productService.searchProducts(keyword, pageable);
    }
}