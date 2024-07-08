package gift.repository;

import gift.model.Product;

import java.util.List;

public interface ProductRepository {
    Product save(Product product);

    void update(Product product);

    Product findById(Long id);

    List<Product> findAll();

    void deleteById(Long id);
}
