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
        return productRepository.findAll();
    }

    public List<Product> getAllProductsByIds(List<Long> productIds) {
        return productRepository.findByIdIn(productIds);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Product product) {
        productRepository.findById(product.getId())
            .orElseThrow(() -> new RuntimeException("Product Not Found with id " + product.getId()));
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Product not found with id " + id);
        }

        productRepository.delete(productOptional.get());
    }
}
