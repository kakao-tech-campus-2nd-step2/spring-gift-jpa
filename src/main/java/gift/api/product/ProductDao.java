package gift.api.product;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final ProductRepository productRepository;

    public ProductDao(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Long insert(ProductRequest productRequest) {
        return productRepository.save(new Product(
            productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl())).getId();
    }

    public void update(long id, ProductRequest productRequest) {
        productRepository.save(new Product(
            id, productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl()));
    }

    public void delete(long id) {
        productRepository.deleteById(id);
    }
}
