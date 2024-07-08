package gift.service;

import gift.repository.ProductRepository;
import gift.entity.Product;
import gift.exception.ProductNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        try {
            return productRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new ProductNotFoundException("해당 id를 가지고있는 Product 객체가 없습니다.");
        }
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
