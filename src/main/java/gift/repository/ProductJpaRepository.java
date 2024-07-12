package gift.repository;

import gift.domain.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String productName);

    boolean existsByName(String productName);
}
