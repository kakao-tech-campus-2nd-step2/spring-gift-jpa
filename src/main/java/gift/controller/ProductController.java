package gift.controller;

import gift.model.Product;

import gift.repository.ProductRepository;
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

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public Product addProduct(@RequestBody Product newProduct) {
        return productRepository.save(newProduct);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable(value = "id") long id) {
        return productRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable(value = "id") long id) {
        productRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable("id") long id, @RequestBody Product updatedProduct) {
        productRepository.save(toEntity(updatedProduct, id));
        return productRepository.findById(id).orElse(null);
    }

    private static Product toEntity(Product product, Long id) {
        return new Product(id, product.getName(), product.getPrice(), product.getImageUrl());
    }
}
