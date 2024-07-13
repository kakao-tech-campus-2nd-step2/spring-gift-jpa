package gift.controller;

import gift.exception.InvalidProductException;
import gift.exception.ProductNotFoundException;
import gift.model.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController( ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody @Valid Product product, RedirectAttributes redirectAttributes) {
        Product addedProduct = productService.addProduct(product);
        return ResponseEntity.ok(addedProduct);
    }

    //@PostMapping("/{id}")

    public ResponseEntity<Product> updateProduct(@Valid @PathVariable Long id, @RequestBody Product product, RedirectAttributes redirectAttributes) {
        productService.updateProduct(id, product);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().body("delete complete!");
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Product> getProductDetails(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Product> product = productService.getProductById(id);
        model.addAttribute("product", product);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}