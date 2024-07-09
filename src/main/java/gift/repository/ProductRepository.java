package gift.repository;

import gift.dto.request.ProductRequest;
import gift.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
    List<Product> findAll();
    Optional<Product> updateById(Long id, ProductRequest productRequest);
    Optional<Product> deleteById(Long id);
}
