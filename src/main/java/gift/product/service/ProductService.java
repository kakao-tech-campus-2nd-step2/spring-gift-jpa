package gift.product.service;

import gift.product.exception.ProductAlreadyExistsException;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(@Valid Product product) {
        checkForDuplicateProduct(product);
        return productRepository.save(product);
    }

    public Product updateProduct(@Valid Product product) {
        checkForDuplicateProduct(product);
        return productRepository.update(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void checkForDuplicateProduct(Product product) {
        List<Product> products = productRepository.findAll();
        for (Product p : products) {
            if (p.equalProduct(product)) {
                throw new ProductAlreadyExistsException(product.getName());
            }
        }
    }
}
