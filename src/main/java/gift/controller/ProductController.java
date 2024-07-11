package gift.controller;

import gift.dto.ProductRequest;
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
    public Product addProduct(@Valid @RequestBody ProductRequest productRequest) {
        productService.saveProduct(productRequest);
        return new Product(productRequest.getName(), productRequest.getPrice(),
            productRequest.getImg());
    }

    @PutMapping("/{id}")
    public Product changeProduct(@PathVariable("id") Long id,
        @Valid @RequestBody ProductRequest productRequest) {
        productService.updateProduct(id, productRequest);
        return new Product(id, productRequest.getName(), productRequest.getPrice(),
            productRequest.getImg());
    }

    @DeleteMapping("/{id}")
    public void removeProduct(@PathVariable("id") Long id) {
        productService.getProductById(id);
        productService.deleteProduct(id);
    }
}
