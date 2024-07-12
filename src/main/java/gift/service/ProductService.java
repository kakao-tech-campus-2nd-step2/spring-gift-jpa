package gift.service;

import gift.repository.ProductRepository;
import gift.model.Product;
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
        return productRepository.findById(id);
    }

    @Transactional
    public Product createProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    @Transactional
    public Product updateProduct(long id, Product product) {
        product.setId(id);
        productRepository.update(product);
        return product;
    }

    @Transactional
    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }
}
