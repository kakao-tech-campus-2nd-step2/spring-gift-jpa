package gift.doamin.product.repository;

import gift.doamin.product.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByName(String name);
}
