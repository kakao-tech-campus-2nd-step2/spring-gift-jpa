package gift.product.service;

import gift.product.dto.ServiceDto;
import gift.product.domain.Product;
import gift.product.exception.ProductNotFoundException;
import gift.product.repository.ProductRepository;
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
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    public void createProduct(ServiceDto serviceDto) {
        productRepository.save(serviceDto.toProduct());
    }

    public void updateProduct(ServiceDto serviceDto) {
        validateProductExists(serviceDto.id());
        productRepository.save(serviceDto.toProduct());
    }

    public void deleteProduct(Long id) {
        validateProductExists(id);
        productRepository.deleteById(id);
    }

    private void validateProductExists(Long id) {
        productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }
}
