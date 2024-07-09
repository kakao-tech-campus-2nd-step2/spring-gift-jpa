package gift.repository;

import gift.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Long save(Product product);
    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
    Optional<List<Product>> findByIds(List<Long> ids);
    List<Product> findAll();
    Long delete(Long id);
    Product update(Long id, Product product);
}
