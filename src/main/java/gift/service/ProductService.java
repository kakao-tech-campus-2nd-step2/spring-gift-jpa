package gift.service;

import gift.exception.product.ProductNotFoundException;
import gift.model.Product;
import gift.repository.ProductRepository;
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
        checkProductExist(id);
        return productRepository.findById(id);
    }

    public void addProduct(Product product) {
        productRepository.insert(product);
    }

    public void updateProduct(Long id, Product product) {
        checkProductExist(id);
        productRepository.update(id, product);
    }

    public void deleteProduct(Long id) {
        checkProductExist(id);
        productRepository.delete(id);
    }

    public void checkProductExist(Long id) {
        Product product = productRepository.findById(id);

        if(product == null) {
            throw new ProductNotFoundException("해당 ID의 상품을 찾을 수 없습니다.");
        }
    }
}
