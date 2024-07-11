package gift.service;

import gift.model.Product;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        validateProductName(product.name());
        return productRepository.save(product);
    }

    public void updateProduct(Product product) {
        validateProductName(product.name());
        productRepository.update(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private void validateProductName(String productName) {
        if (productName.contains("카카오")) {
            throw new IllegalArgumentException("'카카오'를 이름에 포함하려면 담당 MD와 협의해주세요.");
        }
    }
}