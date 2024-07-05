package gift.product.service;

import gift.product.dto.ProductServiceDto;
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

    public void createProduct(ProductServiceDto productServiceDto) {
        productRepository.save(productServiceDto.toProduct());
    }

    public void updateProduct(ProductServiceDto productServiceDto) {
        validateProductExists(productServiceDto.id());
        productRepository.save(productServiceDto.toProduct());
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
