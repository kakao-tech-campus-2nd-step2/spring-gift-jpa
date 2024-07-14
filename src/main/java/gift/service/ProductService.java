package gift.service;

import gift.model.Product;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Invalid product Id:" + id);
        }
        return product.get();
    }

    public Product saveProduct(Product product) {
        validateProductName(product.getName());
        return productRepository.save(product);
    }

    public void updateProduct(Product product) {
        validateProductName(product.getName());
        if (!productRepository.existsById(product.getId())) {
            throw new IllegalArgumentException("Invalid product Id:" + product.getId());
        }
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Invalid product Id:" + id);
        }
        productRepository.deleteById(id);
    }

    private void validateProductName(String productName) {
        if (productName.contains("카카오")) {
            throw new IllegalArgumentException("'카카오'를 이름에 포함하려면 담당 MD와 협의해주세요.");
        }
    }
}