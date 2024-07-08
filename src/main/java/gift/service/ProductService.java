package gift.service;

import gift.model.Product;
import gift.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Service
@Validated
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void createProduct(@Valid Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Long id, @Valid Product product) {
        productRepository.update(id, product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
