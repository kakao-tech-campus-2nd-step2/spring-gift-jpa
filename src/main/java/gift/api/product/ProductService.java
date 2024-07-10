package gift.api.product;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final EntityManager entityManager;
    private final ProductRepository productRepository;

    public ProductService(EntityManager entityManager, ProductRepository productRepository) {
        this.entityManager = entityManager;
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Long add(ProductRequest productRequest) {
        Product product = new Product(
            productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());
        return productRepository.save(product).getId();
    }

    @Transactional
    public void update(Long id, ProductRequest productRequest) {
        Product product = entityManager.find(Product.class, id);
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
