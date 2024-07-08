package gift.service;

import gift.dto.Product;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.getProducts();
    }

    public Long addProduct(Product product) {
        return productRepository.addProduct(product);
    }

    public boolean updateProduct(Product product) {
        return productRepository.updateProduct(product);
    }

    public boolean deleteProduct(Long id) {
        return productRepository.deleteProduct(id);
    }

    public Product getProduct(Long productId) {
        return productRepository.getProduct(productId);
    }

}
