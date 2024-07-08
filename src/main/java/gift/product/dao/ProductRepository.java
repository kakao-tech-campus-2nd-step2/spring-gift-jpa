package gift.product.dao;

import gift.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    void deleteById(Long id);
    void deleteAll();
    int update(Long id, Product product);
}
