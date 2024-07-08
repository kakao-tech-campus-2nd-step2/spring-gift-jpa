package gift.service;


import gift.domain.Product;
import gift.repository.product.ProductH2Repository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
//    private final ProductMemoryRepository productMemoryRepository;
    private final ProductH2Repository productH2Repository;

//    public ProductService(ProductMemoryRepository productMemoryRepository) {
//        this.productMemoryRepository = productMemoryRepository;
//    }
    public ProductService(ProductH2Repository productH2Repository) {
        this.productH2Repository = productH2Repository;
    }

    public Product getProductById(Long id) {
        return productH2Repository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() {
        return productH2Repository.findAll();
    }

    public Product createProduct(Product product) {
        Product savedproduct = productH2Repository.save(product);
        return savedproduct;
    }

    public void updateProduct(Long id, Product updatedProduct) {
        productH2Repository.update(id, updatedProduct);
    }

    public void deleteProduct(Long id) {
        productH2Repository.deleteById(id);
//        productH2Repository.orderId();
    }
}
