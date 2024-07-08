package gift.product.controller;

import gift.product.model.Product;
import gift.product.service.AdminProductService;
import gift.product.validation.ProductValidation;
import jakarta.validation.Valid;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/product")
public class ApiProductController {

    private final AdminProductService adminProductService;
    private final AtomicLong idCounter = new AtomicLong();
    private final ProductValidation productValidation;

    @Autowired
    public ApiProductController(AdminProductService adminProductService, ProductValidation productValidation) {
        this.adminProductService = adminProductService;
        this.productValidation = productValidation;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> showProductList() {
        System.out.println("[ProductController] showProductList()");
        List<Product> productList = new ArrayList<>(adminProductService.getAllProducts());
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/register")
    public ResponseEntity<Product> showProductForm() {
        System.out.println("[ProductController] showProductForm()");
        Product product = new Product(idCounter.incrementAndGet(), "", 0, "");
        return ResponseEntity.ok(product);
    }

    @PostMapping()
    public ResponseEntity<String> registerProduct(@Valid @RequestBody Product product) {
        System.out.println("[ProductController] registerProduct()");
        productValidation.isIncludeKakao(product.getName());
        adminProductService.registerProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product registered successfully");
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<Product> updateProductForm(@PathVariable Long id) {
        System.out.println("[ProductController] updateProductForm()");
        Product product = adminProductService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping()
    public ResponseEntity<String> updateProduct(@Valid @RequestBody Product product) {
        System.out.println("[ProductController] updateProduct()");
        productValidation.isIncludeKakao(product.getName());
        adminProductService.updateProduct(product);
        return ResponseEntity.ok("Product updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        System.out.println("[ProductController] deleteProduct()");
        if (productValidation.existsById(id)) {
            adminProductService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam("keyword") String keyword) {
        System.out.println("[ProductController] searchProduct()");
        List<Product> searchResults = new ArrayList<>(adminProductService.searchProducts(keyword));
        return ResponseEntity.ok(searchResults);
    }
}
