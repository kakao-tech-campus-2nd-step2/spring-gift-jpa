package gift.service;

import gift.exception.ProductException;
import gift.model.Product;
import gift.repository.ProductDao;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductException("상품이 존재하지 않습니다.");
        }
        return product.get();
    }

    public void insertProduct(String name, Integer price, String imageUrl) {
        Product product = new Product(name, price, imageUrl);
        productRepository.save(product);
    }

    public void updateProduct(Long id, String name, Integer price, String imageUrl) {
        Product product = new Product(id, name, price, imageUrl);
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
