package gift.controller;

import gift.domain.Product;
import gift.dto.ProductDTO;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public List<Product> getProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/paged")
    public Page<Product> getProducts(Pageable pageable) {
        return productService.findAllProducts(pageable);
    }

    @PostMapping
    public Product postProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = productDTO.toEntity();
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public Product putProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        Product product = new Product.ProductBuilder()
            .id(id)
            .name(productDTO.getName())
            .price(productDTO.getPrice())
            .imageUrl(productDTO.getImageUrl())
            .description(productDTO.getDescription())
            .build();
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public Long deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
