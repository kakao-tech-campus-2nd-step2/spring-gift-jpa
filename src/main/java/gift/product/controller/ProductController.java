package gift.product.controller;

import gift.product.dto.ProductDto;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public void addProduct(@Valid @RequestBody ProductDto productDto) {
        productService.save(productDto);
    }

    @PutMapping("/edit/{product_id}")
    public void editProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        productService.update(id, productDto);
    }

    @DeleteMapping("/delete/{product_id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }
}