package gift.service;

import gift.dto.ProductRequest;
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
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("해당 id를 가지고있는 Product 객체가 없습니다."));
    }

    public void saveProduct(ProductRequest productRequest) {
        Product product = new Product(productRequest.getName(), productRequest.getPrice(),
            productRequest.getImg());
        productRepository.save(product);
    }

    public void updateProduct(Long id, ProductRequest productRequest) {
        Product product = new Product(id, productRequest.getName(), productRequest.getPrice(),
            productRequest.getImg());
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
