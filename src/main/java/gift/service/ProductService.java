package gift.service;

import gift.DTO.Product;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public List<Product> getAllProductsByIds(List<Long> productIds) {
        return productRepository.getAllProductsByIds(productIds);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.getProductById(id);
    }

    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    public void updateProduct(Product product) {
        productRepository.updateProduct(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteProduct(id);
    }
}
