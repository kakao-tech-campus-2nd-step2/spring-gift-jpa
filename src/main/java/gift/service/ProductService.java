package gift.service;

import gift.domain.product.Product;
import gift.domain.product.ProductRequest;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> readProductAll() {
        return productRepository.findAll();
    }

    public Product readProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(ProductRequest productRequest) {
        return productRepository.insert(productRequest);
    }

    public Product updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.update(id, productRequest);
    }

    public void deleteProduct(Long id) {
        productRepository.delete(id);
    }
}
