package gift.product.repository;

import gift.product.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);
    boolean existsByName(String name);
}
