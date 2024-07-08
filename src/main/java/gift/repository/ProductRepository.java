package gift.repository;

import gift.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
  List<Product> findAll();
  Optional<Product> findById(Long id);
  int save(Product product);
  int update(Product product);
  int deleteById(Long id);
}

