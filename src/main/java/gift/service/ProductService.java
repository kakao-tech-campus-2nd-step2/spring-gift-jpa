package gift.service;

import gift.model.Product;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Product getProductById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
    }

    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(long id, Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }
}
