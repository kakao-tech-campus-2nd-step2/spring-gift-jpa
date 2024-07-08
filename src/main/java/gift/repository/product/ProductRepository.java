package gift.repository.product;

import gift.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();

    Long update(Long id, Product product);
    Long delete(Long id);

}
