package gift.repository;

import gift.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProductRepository {
    List<Product> findAll();
    Product findById(Long id);
    boolean save(Product product);
    boolean update(Product product);
    boolean delete(Long id);
    List<Product> findPaginated(int page, int size);
}
