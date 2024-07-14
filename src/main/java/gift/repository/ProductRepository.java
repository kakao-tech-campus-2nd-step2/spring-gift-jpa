package gift.repository;

import gift.domain.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    Product findById(Long id);
    void save(Product product);
    void update(Product product);
    void deleteById(Long id);
}
