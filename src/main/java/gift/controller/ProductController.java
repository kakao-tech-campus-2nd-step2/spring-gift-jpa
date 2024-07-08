package gift.controller;

import gift.entity.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProduct() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getOneProduct(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product addProduct(@Valid @RequestBody Product product) {
        productService.saveProduct(product);
        return product;
    }

    @PutMapping("/{id}")
    public Product changeProduct(@PathVariable("id") Long id, @Valid @RequestBody Product product) {
        productService.getProductById(id);
        product.setId(id);
        productService.saveProduct(product);
        return product;
    }

    @DeleteMapping("/{id}")
    public void removeProduct(@PathVariable("id") Long id) {
        productService.getProductById(id);
        productService.deleteProduct(id);
    }
}
