package gift.repository.product;

import gift.domain.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    void save(Product product);
    void update(Product product);
    void delete(Long id);
}
